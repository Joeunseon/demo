package com.project.demo.api.code.application;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.project.demo.api.code.infrastructure.CmmCdRepository;
import com.project.demo.api.common.application.dto.SelectBoxDTO;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.constant.CommonMsgKey;
import com.project.demo.common.util.MsgUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CodeService {

    private final CmmCdRepository cmmCdRepository;
    private final MsgUtil msgUtil;

    public ApiResponse<List<SelectBoxDTO>> getCode(String grpCd) {

        try {

            return ApiResponse.success(cmmCdRepository.findSelectOptions(grpCd));
        } catch (Exception e) {
            log.error(">>>> CodeService::getCode: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
        }
    }
}
