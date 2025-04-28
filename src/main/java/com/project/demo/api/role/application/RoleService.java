package com.project.demo.api.role.application;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.demo.api.role.application.dto.RoleCreateDTO;
import com.project.demo.api.role.application.dto.RoleDetailDTO;
import com.project.demo.api.role.application.dto.RoleRequestDTO;
import com.project.demo.api.role.application.dto.RoleUpdateDTO;
import com.project.demo.api.role.domain.RoleEntity;
import com.project.demo.api.role.infrastructure.RoleRepository;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.constant.CommonConstant.MODEL_KEY;
import com.project.demo.common.constant.CommonMsgKey;
import com.project.demo.common.constant.DelYn;
import com.project.demo.common.constant.MenuMsgKey;
import com.project.demo.common.util.MsgUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {

    private final RoleRepository roleRepository;
    private final MsgUtil msgUtil;

    public ApiResponse<Map<String, Object>> findAll(RoleRequestDTO dto) {

        try {
            Map<String, Object> dataMap = new HashMap<>();

            Long totalCnt = roleRepository.countBySearch(dto);

            if ( totalCnt > 0 )
                dataMap.put(MODEL_KEY.RESULT_LIST, roleRepository.findBySearch(dto));

            dataMap.put(MODEL_KEY.TOTAL_CNT, totalCnt);

            dataMap.put(MODEL_KEY.CURRENT_PAGE, dto.getPage());
            dataMap.put(MODEL_KEY.PAGE_SCALE, dto.getPageScale());
            
            return ApiResponse.success(dataMap);
        } catch (Exception e) {
            log.error(">>>> RoleService::findAll: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
        }
    }

    public ApiResponse<RoleDetailDTO> findById(Integer roleSeq) {

        try {
            
            return ApiResponse.success(roleRepository.findByRoleSeq(roleSeq));
        } catch (Exception e) {
            log.error(">>>> RoleService::findById: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
        }
    }

    public ApiResponse<Void> checkDuplicate(String roleNm, Integer roleSeq) {

        try {

            Integer count;

            if ( roleSeq != null ) {
                count = roleRepository.countByRoleNmAndDelYnAndRoleSeqNot(roleNm.trim(), DelYn.N, roleSeq);
            } else {
                count = roleRepository.countByRoleNmAndDelYn(roleNm.trim(), DelYn.N);
            }

            if ( count > 0 )
                return ApiResponse.error(HttpStatus.BAD_REQUEST, msgUtil.getMessage(MenuMsgKey.FAILED_DUPLICATION.getKey(), "권한 이름"));
            
            return ApiResponse.success(msgUtil.getMessage(MenuMsgKey.SUCCUESS_DUPLICATION.getKey(), "권한 이름"));
        } catch (Exception e) {
            log.error(">>>> RoleService::checkDuplicate: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
        }
    }

    public ApiResponse<Integer> create(RoleCreateDTO dto) {

        try {
            if ( dto.getUserSessionDTO() == null || dto.getUserSessionDTO().getUserSeq() == null ) 
                return ApiResponse.error(HttpStatus.FORBIDDEN, msgUtil.getMessage(CommonMsgKey.FAILED_FORBIDDEN.getKey()));

            // 1. 중복 확인
            ApiResponse<Void> check = checkDuplicate(dto.getRoleNm(), null);

            if ( check.getStatus() != HttpStatus.OK )
                return ApiResponse.error(check.getStatus(), check.getMessage());
            
            // 2. 권한 등록
            RoleEntity entity = roleRepository.save(dto.toEntity(dto.getUserSessionDTO().getUserSeq()));

            return ApiResponse.success(msgUtil.getMessage(CommonMsgKey.SUCCUESS.getKey()), entity.getRoleSeq());
        } catch (Exception e) {
            log.error(">>>> RoleService::create: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
        }
    }

    @Transactional(readOnly = false)
    public ApiResponse<Integer> update(RoleUpdateDTO dto) {

        try {
            if ( dto.getUserSessionDTO() == null || dto.getUserSessionDTO().getUserSeq() == null ) 
                return ApiResponse.error(HttpStatus.FORBIDDEN, msgUtil.getMessage(CommonMsgKey.FAILED_FORBIDDEN.getKey()));

            // 1. 중복 확인
            ApiResponse<Void> check = checkDuplicate(dto.getRoleNm(), dto.getRoleSeq());

            if ( check.getStatus() != HttpStatus.OK )
                return ApiResponse.error(check.getStatus(), check.getMessage());

            // 2. 권한 수정
            roleRepository.updateById(dto.toEntity(dto.getUserSessionDTO().getUserSeq()));

            return ApiResponse.success(msgUtil.getMessage(CommonMsgKey.SUCCUESS.getKey()), dto.getRoleSeq());
        } catch (Exception e) {
            log.error(">>>> RoleService::update: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
        }
    }
}
