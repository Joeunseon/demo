package com.project.demo.api.code.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.project.demo.api.code.application.dto.CdDetailDTO;
import com.project.demo.api.code.application.dto.CodeCreateDTO;
import com.project.demo.api.code.application.dto.CodeRequestDTO;
import com.project.demo.api.code.application.dto.GrpDetailDTO;
import com.project.demo.api.code.domain.CmmCdEntity;
import com.project.demo.api.code.domain.CmmCdGrpEntity;
import com.project.demo.api.code.infrastructure.CmmCdGrpRepository;
import com.project.demo.api.code.infrastructure.CmmCdRepository;
import com.project.demo.api.code.value.CodeType;
import com.project.demo.api.common.application.dto.SelectBoxDTO;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.constant.CommonConstant.MODEL_KEY;
import com.project.demo.common.constant.CommonMsgKey;
import com.project.demo.common.constant.MenuMsgKey;
import com.project.demo.common.exception.CustomException;
import com.project.demo.common.util.MsgUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CodeService {

    private final CmmCdGrpRepository cmmCdGrpRepository;
    private final CmmCdRepository cmmCdRepository;
    private final MsgUtil msgUtil;

    public ApiResponse<List<SelectBoxDTO>> getCode(String grpCd) {

        try {
            if ( StringUtils.hasText(grpCd) ) {
                return ApiResponse.success(cmmCdRepository.findAllByGrpCd(grpCd));
            } else {
                return ApiResponse.success(cmmCdGrpRepository.findAllOrderByRegDtAsc());
            }
        } catch (Exception e) {
            log.error(">>>> CodeService::getCode: ", e);
            throw new CustomException(msgUtil.getMessage(CommonMsgKey.FAILED.getKey()), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public ApiResponse<Map<String, Object>> findAll(CodeRequestDTO dto) {

        try {
            Map<String, Object> dataMap = new HashMap<>();
            
            Long totalCnt = cmmCdRepository.countBySearch(dto);

            if ( totalCnt > 0 )
                dataMap.put(MODEL_KEY.RESULT_LIST, cmmCdRepository.findBySearch(dto));

            dataMap.put(MODEL_KEY.TOTAL_CNT, totalCnt);
            dataMap.put(MODEL_KEY.CURRENT_PAGE, dto.getPage());
            dataMap.put(MODEL_KEY.PAGE_SCALE, dto.getPageScale());

            return ApiResponse.success(dataMap);
        } catch (Exception e) {
            log.error(">>>> CodeService::findAll: ", e);
            throw new CustomException(msgUtil.getMessage(CommonMsgKey.FAILED.getKey()), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public ApiResponse<GrpDetailDTO> findByGrp(Integer grpSeq) {

        try {
            // 1. 그룹코드 조회
            Optional<CmmCdGrpEntity> grpOptional = cmmCdGrpRepository.findById(Long.valueOf(grpSeq));

            if ( !grpOptional.isEmpty() ) {
                CmmCdGrpEntity grpEntity = grpOptional.get();

                // 2. 그룹코드 DTO 생성
                GrpDetailDTO grpDetailDTO = GrpDetailDTO.of(grpEntity);

                // 3. 하위 코드 리스트 조회
                List<CmmCdEntity> cdEntityList = cmmCdRepository.findByCmmCdGrp(grpEntity);

                // 4. 하위 코드 DTO 변환
                List<CdDetailDTO> children = cdEntityList.stream()
                                                        .map(CdDetailDTO::of)
                                                        .collect(Collectors.toList());
                
                // 5. 하위 코드 리스트 설정
                grpDetailDTO.ofChildren(children);

                return ApiResponse.success(grpDetailDTO);
            }

            return ApiResponse.success();
        } catch (Exception e) {
            log.error(">>>> CodeService::findByGrp: ", e);
            throw new CustomException(msgUtil.getMessage(CommonMsgKey.FAILED.getKey()), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public ApiResponse<CdDetailDTO> findByCd(Integer cdSeq) {

        try {
            Optional<CmmCdEntity> cdOptional = cmmCdRepository.findById(Long.valueOf(cdSeq));

            return cdOptional.map(info -> ApiResponse.success(CdDetailDTO.of(info)))
                            .orElse(ApiResponse.success());
        } catch (Exception e) {
            log.error(">>>> CodeService::findByCd: ", e);
            throw new CustomException(msgUtil.getMessage(CommonMsgKey.FAILED.getKey()), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional(readOnly = false)
    public ApiResponse<Integer> createGrp(CodeCreateDTO dto) {

        try {
            if ( dto.getUserSessionDTO() == null || dto.getUserSessionDTO().getUserSeq() == null ) 
                return ApiResponse.error(HttpStatus.FORBIDDEN, msgUtil.getMessage(CommonMsgKey.FAILED_FORBIDDEN.getKey()));

            CmmCdGrpEntity entity = cmmCdGrpRepository.save(dto.toGrpEntity(dto.getUserSessionDTO().getUserSeq()));

            return ApiResponse.success(msgUtil.getMessage(CommonMsgKey.SUCCESS.getKey()), entity.getGrpSeq());
        } catch (Exception e) {
            log.error(">>>> CodeService::createGrp: ", e);
            throw new CustomException(msgUtil.getMessage(CommonMsgKey.FAILED.getKey()), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional(readOnly = false)
    public ApiResponse<Integer> createCd(CodeCreateDTO dto) {

        try {
            if ( dto.getUserSessionDTO() == null || dto.getUserSessionDTO().getUserSeq() == null ) 
                return ApiResponse.error(HttpStatus.FORBIDDEN, msgUtil.getMessage(CommonMsgKey.FAILED_FORBIDDEN.getKey()));

            CmmCdEntity entity = cmmCdRepository.save(dto.toCdEntity(dto.getUserSessionDTO().getUserSeq()));

            return ApiResponse.success(msgUtil.getMessage(CommonMsgKey.SUCCESS.getKey()), entity.getCdSeq());
        } catch (Exception e) {
            log.error(">>>> CodeService::createCd: ", e);
            throw new CustomException(msgUtil.getMessage(CommonMsgKey.FAILED.getKey()), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public ApiResponse<Void> checkDuplicateCd(String cd, CodeType codeType) {

        try {
            
            if ( codeType == CodeType.GRP ) {
                if ( cmmCdGrpRepository.countByGrpCd(cd) > 0 )
                    return ApiResponse.error(HttpStatus.BAD_REQUEST, msgUtil.getMessage(MenuMsgKey.FAILED_DUPLICATION.getKey(), "코드")); 
            }

            if ( codeType == CodeType.CD ) {
                if ( cmmCdRepository.countByCd(cd) > 0 )
                    return ApiResponse.error(HttpStatus.BAD_REQUEST, msgUtil.getMessage(MenuMsgKey.FAILED_DUPLICATION.getKey(), "코드")); 
            }

            return ApiResponse.success(msgUtil.getMessage(MenuMsgKey.SUCCESS_DUPLICATION.getKey(), "코드"));
        } catch (Exception e) {
            log.error(">>>> CodeService::checkDuplicateCd: ", e);
            throw new CustomException(msgUtil.getMessage(CommonMsgKey.FAILED.getKey()), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public ApiResponse<Void> checkDuplicateCdNm(String cdNm, CodeType codeType) {

        try {
            if ( codeType == CodeType.GRP ) {
                if ( cmmCdGrpRepository.countByGrpNm(cdNm) > 0 )
                    return ApiResponse.error(HttpStatus.BAD_REQUEST, msgUtil.getMessage(MenuMsgKey.FAILED_DUPLICATION.getKey(), "코드 이름")); 
            }

            if ( codeType == CodeType.CD ) {
                if ( cmmCdRepository.countByCdNm(cdNm) > 0 )
                    return ApiResponse.error(HttpStatus.BAD_REQUEST, msgUtil.getMessage(MenuMsgKey.FAILED_DUPLICATION.getKey(), "코드 이름"));
            }

            return ApiResponse.success(msgUtil.getMessage(MenuMsgKey.SUCCESS_DUPLICATION.getKey(), "코드 이름"));
        } catch (Exception e) {
            log.error(">>>> CodeService::checkDuplicateCdNm: ", e);
            throw new CustomException(msgUtil.getMessage(CommonMsgKey.FAILED.getKey()), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }
}
