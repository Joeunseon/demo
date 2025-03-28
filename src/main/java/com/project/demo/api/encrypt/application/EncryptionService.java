package com.project.demo.api.encrypt.application;

import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.demo.api.encrypt.application.dto.Base64RequestDTO;
import com.project.demo.api.encrypt.application.dto.BcryptRequestDTO;
import com.project.demo.api.encrypt.application.dto.HashRequestDTO;
import com.project.demo.api.encrypt.application.dto.JasyptRequestDTO;
import com.project.demo.api.encrypt.infrastructure.Base64Util;
import com.project.demo.api.encrypt.infrastructure.HashUtil;
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

    public ApiResponse<String> bcryptEncrypt(BcryptRequestDTO dto) {

        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            String encryptStr = encoder.encode(dto.getTargetText());
            return ApiResponse.success(msgUtil.getMessage(CommonMsgKey.SUCCUESS.getKey()), encryptStr);
        } catch (Exception e) {
            log.error(">>>> EncryptionService::bcryptEncrypt: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
        }
    }

    public ApiResponse<Void> bcryptMatches(BcryptRequestDTO dto) {

        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            return ApiResponse.success(msgUtil.getMessage((encoder.matches(dto.getTargetText(), dto.getSecretKey()) ? EncryptionMsgKey.SUCCUESS_MATCHES_Y.getKey() : EncryptionMsgKey.SUCCUESS_MATCHES_N.getKey())));
        } catch (Exception e) {
            log.error(">>>> EncryptionService::bcryptEncrypt: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
        }
    }

    public ApiResponse<String> base64Encode(Base64RequestDTO dto) {

        try {
            String encodeStr = Base64Util.encode(dto.getTargetText());

            return ApiResponse.success(msgUtil.getMessage(CommonMsgKey.SUCCUESS.getKey()), encodeStr);
        } catch (Exception e) {
            log.error(">>>> EncryptionService::base64Encode: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
        }
    }

    public ApiResponse<String> base64Decode(Base64RequestDTO dto) {
        
        try {
            String decodeStr = Base64Util.decode(dto.getTargetText());

            return ApiResponse.success(msgUtil.getMessage(CommonMsgKey.SUCCUESS.getKey()), decodeStr);
        } catch (IllegalArgumentException e) {
            log.error(">>>> EncryptionService::base64Decode: ", msgUtil.getMessage(EncryptionMsgKey.FAILED_DECODE.getKey(), "base64"));
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(EncryptionMsgKey.FAILED_BASE64_DECODE.getKey()));
        } catch (Exception e) {
            log.error(">>>> EncryptionService::base64Decode: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
        }
    }

    public ApiResponse<String> hashEncrypt(HashRequestDTO dto) {

        try {
            String encryptStr = HashUtil.encrypt(dto.getTargetText(), dto.getAlgorithm().getAlgorithm());

            return ApiResponse.success(msgUtil.getMessage(CommonMsgKey.SUCCUESS.getKey()), encryptStr);
        } catch (IllegalArgumentException e) {
            log.error(">>>> EncryptionService::hashEncrypt: ", msgUtil.getMessage(EncryptionMsgKey.FAILED_ENCRYPT.getKey(), "hash"));
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(EncryptionMsgKey.FAILED_HASH_ENCRYPT.getKey()));
        } catch (Exception e) {
            log.error(">>>> EncryptionService::hashEncrypt: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
        }
    }
}
