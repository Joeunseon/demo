package com.project.demo.api.log.application;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.project.demo.api.log.application.dto.LogDetailDTO;
import com.project.demo.api.log.application.dto.LogRequestDTO;
import com.project.demo.api.log.application.dto.LogResolveDTO;
import com.project.demo.api.log.infrastructure.ErrLogRepository;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.constant.CommonConstant.MODEL_KEY;
import com.project.demo.common.constant.CommonMsgKey;
import com.project.demo.common.util.MsgUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ErrLogService {

    private final ErrLogRepository errLogRepository;
    private final MsgUtil msgUtil;

    public ApiResponse<Map<String, Object>> findAll(LogRequestDTO dto) {
        
        try {
            Map<String, Object> dataMap = new HashMap<>();

            Long totalCnt = errLogRepository.countBySearch(dto);

            if ( totalCnt > 0 )
                dataMap.put(MODEL_KEY.RESULT_LIST, errLogRepository.findBySearch(dto));

            dataMap.put(MODEL_KEY.TOTAL_CNT, totalCnt);

            dataMap.put(MODEL_KEY.CURRENT_PAGE, dto.getPage());
            dataMap.put(MODEL_KEY.PAGE_SCALE, dto.getPageScale());

            return ApiResponse.success(dataMap);
        } catch (Exception e) {
            log.error(">>>> ErrLogService::findAll: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
        }
    }

    public ApiResponse<LogDetailDTO> findById(Long logSeq) {

        try {
            
            return ApiResponse.success(errLogRepository.findByLogSeq(logSeq));
        } catch (Exception e) {
            log.error(">>>> ErrLogService::findById: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
        }
    }

    public ApiResponse<Void> updateResolve(LogResolveDTO dto) {

        try {

            if ( dto.getLogSeq() != null ) { // 단건

            } else if ( dto.getLogSeqList() != null && !dto.getLogSeqList().isEmpty() ) { // 복수건

            } else {
                return ApiResponse.error(HttpStatus.BAD_REQUEST, msgUtil.getMessage(CommonMsgKey.FAILED_REQUEST.getKey()));
            }
            
            return ApiResponse.success(msgUtil.getMessage(CommonMsgKey.SUCCUESS.getKey()));
        } catch (Exception e) {
            log.error(">>>> ErrLogService::updateResolve: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
        }
    }
}
