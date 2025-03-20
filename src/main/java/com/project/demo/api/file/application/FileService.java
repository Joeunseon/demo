package com.project.demo.api.file.application;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.demo.common.ApiResponse;
import com.project.demo.common.BaseDTO;
import com.project.demo.common.constant.CommonMsgKey;
import com.project.demo.common.util.MsgUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    //private final FileMstrRepository fileMstrRepository;
    //private final FileDtlRepository fileDtlRepository;

    private final MsgUtil msgUtil;

    public ApiResponse<Long> create(MultipartFile[] files, BaseDTO dto) {

        if ( files == null || files.length == 0 )
            return ApiResponse.error(HttpStatus.BAD_REQUEST, msgUtil.getMessage(CommonMsgKey.FAILED_REQUEST.getKey()));
        
        try {
            if ( dto.getUserSessionDTO() == null || dto.getUserSessionDTO().getUserSeq() == null )
                return ApiResponse.error(HttpStatus.FORBIDDEN, msgUtil.getMessage(CommonMsgKey.FAILED_FORBIDDEN.getKey()));

            // 1. file_mstr table insert

            // 2. file upload

            // 3. file_dtl table insert


            return ApiResponse.success(1L);
        } catch (Exception e) {
            log.error(">>>> FileService::create: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED_FORBIDDEN.getKey()));
        }
    }
}
