package com.project.demo.api.file.application;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.project.demo.api.file.application.dto.FileDtlListDTO;
import com.project.demo.api.file.domain.FileDtlEntity;
import com.project.demo.api.file.domain.FileMstrEntity;
import com.project.demo.api.file.infrastructure.FileDtlRepository;
import com.project.demo.api.file.infrastructure.FileMstrRepository;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.BaseDTO;
import com.project.demo.common.constant.CommonMsgKey;
import com.project.demo.common.util.FileUtil;
import com.project.demo.common.util.MsgUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    private final FileMstrRepository fileMstrRepository;
    private final FileDtlRepository fileDtlRepository;

    private final MsgUtil msgUtil;
    private final FileUtil fileUtil;

    public ApiResponse<List<FileDtlListDTO>> findById(Long fileSeq) {

        try {

            return ApiResponse.success(msgUtil.getMessage(CommonMsgKey.SUCCUESS.getKey()), fileDtlRepository.findByFileSeq(fileSeq));
        } catch (Exception e) {
            log.error(">>>> FileService::findById: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED_FORBIDDEN.getKey()));
        }
    }

    @Transactional(readOnly = false)
    public ApiResponse<Long> create(MultipartFile[] files, BaseDTO dto) {

        if ( files == null || files.length == 0 )
            return ApiResponse.error(HttpStatus.BAD_REQUEST, msgUtil.getMessage(CommonMsgKey.FAILED_REQUEST.getKey()));
        
        try {
            if ( dto.getUserSessionDTO() == null || dto.getUserSessionDTO().getUserSeq() == null )
                return ApiResponse.error(HttpStatus.FORBIDDEN, msgUtil.getMessage(CommonMsgKey.FAILED_FORBIDDEN.getKey()));

            // 1. file_mstr table insert
            FileMstrEntity entity = fileMstrRepository.save(FileMstrEntity.builder()
                                                                            .regDt(LocalDateTime.now())
                                                                            .regSeq(dto.getUserSessionDTO().getUserSeq())
                                                                            .build());

            // 2. file upload
            List<FileDtlEntity> list = fileUtil.uploadFile(files, entity);

            // 3. file_dtl table insert
            if ( list != null && list.size() > 0 ) {
                fileDtlRepository.saveAll(list);
            }

            return ApiResponse.success(msgUtil.getMessage(CommonMsgKey.SUCCUESS.getKey()), entity.getFileSeq());
        } catch (Exception e) {
            log.error(">>>> FileService::create: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED_FORBIDDEN.getKey()));
        }
    }
}
