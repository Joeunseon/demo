package com.project.demo.api.code.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.project.demo.api.code.application.dto.CodeRequestDTO;
import com.project.demo.api.code.infrastructure.CmmCdGrpRepository;
import com.project.demo.api.code.infrastructure.CmmCdRepository;
import com.project.demo.api.common.application.dto.SelectBoxDTO;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.constant.CommonConstant.MODEL_KEY;
import com.project.demo.common.constant.CommonMsgKey;
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
                return ApiResponse.success(cmmCdRepository.findSelectOptions(grpCd));
            } else {
                return ApiResponse.success(cmmCdGrpRepository.findSelectOptions());
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
}
