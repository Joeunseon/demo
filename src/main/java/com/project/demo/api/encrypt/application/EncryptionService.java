package com.project.demo.api.encrypt.application;

import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.project.demo.api.encrypt.application.dto.JasyptRequestDTO;
import com.project.demo.api.encrypt.infrastructure.JasyptUtil;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.constant.CommonMsgKey;
import com.project.demo.common.constant.EncryptionMsgKey;
import com.project.demo.common.util.MsgUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EncryptionService {

    private final MsgUtil msgUtil;

    public ApiResponse<String> jasyptEncrypt(JasyptRequestDTO dto) {

        try {
            String encryptStr = JasyptUtil.encrypt(dto.getTargetText(), dto.getSecretKey(), dto.getAlgorithm().getAlgorithm());

            return ApiResponse.success(msgUtil.getMessage(CommonMsgKey.SUCCUESS.getKey()), encryptStr);
        } catch (Exception e) {
            log.error(">>>> EncryptionService::jasyptEncrypt: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
        }
    }

    public ApiResponse<String> jasyptDecrypt(JasyptRequestDTO dto) {

        try {
            String decryptStr = JasyptUtil.decrypt(dto.getTargetText(), dto.getSecretKey(), dto.getAlgorithm().getAlgorithm());

            return ApiResponse.success(msgUtil.getMessage(CommonMsgKey.SUCCUESS.getKey()), decryptStr);
        } catch (EncryptionOperationNotPossibleException e) {
            log.error(">>>> EncryptionService::jasyptDecrypt: ", msgUtil.getMessage(EncryptionMsgKey.FAILED_DECRYPT.getKey(), "jasypt"));
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(EncryptionMsgKey.FAILED_JASYPT_DECRYPT.getKey()));
        } catch (Exception e) {
            log.error(">>>> EncryptionService::jasyptDecrypt: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
        }
    }
}
