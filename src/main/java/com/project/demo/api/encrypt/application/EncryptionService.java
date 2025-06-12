package com.project.demo.api.encrypt.application;

import java.security.spec.InvalidKeySpecException;

import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.demo.api.encrypt.application.dto.Base64RequestDTO;
import com.project.demo.api.encrypt.application.dto.BcryptRequestDTO;
import com.project.demo.api.encrypt.application.dto.HashRequestDTO;
import com.project.demo.api.encrypt.application.dto.JasyptRequestDTO;
import com.project.demo.api.encrypt.application.dto.RSARequestDTO;
import com.project.demo.api.encrypt.infrastructure.Base64Util;
import com.project.demo.api.encrypt.infrastructure.HashUtil;
import com.project.demo.api.encrypt.infrastructure.JasyptUtil;
import com.project.demo.api.encrypt.infrastructure.RSAUtil;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.constant.CommonMsgKey;
import com.project.demo.common.constant.EncryptionMsgKey;
import com.project.demo.common.exception.CustomException;
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

            return ApiResponse.success(msgUtil.getMessage(CommonMsgKey.SUCCESS.getKey()), encryptStr);
        } catch (Exception e) {
            log.error(">>>> EncryptionService::jasyptEncrypt: ", e);
            throw e;
        }
    }

    public ApiResponse<String> jasyptDecrypt(JasyptRequestDTO dto) {

        try {
            String decryptStr = JasyptUtil.decrypt(dto.getTargetText(), dto.getSecretKey(), dto.getAlgorithm().getAlgorithm());

            return ApiResponse.success(msgUtil.getMessage(CommonMsgKey.SUCCESS.getKey()), decryptStr);
        } catch (EncryptionOperationNotPossibleException e) {
            log.error(">>>> EncryptionService::jasyptDecrypt: ", msgUtil.getMessage(EncryptionMsgKey.FAILED_DECRYPT.getKey(), "jasypt"));
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(EncryptionMsgKey.FAILED_JASYPT_DECRYPT.getKey()));
        } catch (Exception e) {
            log.error(">>>> EncryptionService::jasyptDecrypt: ", e);
            throw e;
        }
    }

    public ApiResponse<String> bcryptEncrypt(BcryptRequestDTO dto) {

        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            String encryptStr = encoder.encode(dto.getTargetText());
            return ApiResponse.success(msgUtil.getMessage(CommonMsgKey.SUCCESS.getKey()), encryptStr);
        } catch (Exception e) {
            log.error(">>>> EncryptionService::bcryptEncrypt: ", e);
            throw e;
        }
    }

    public ApiResponse<Void> bcryptMatches(BcryptRequestDTO dto) {

        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            return ApiResponse.success(msgUtil.getMessage((encoder.matches(dto.getTargetText(), dto.getSecretKey()) ? EncryptionMsgKey.SUCCESS_MATCHES_Y.getKey() : EncryptionMsgKey.SUCCESS_MATCHES_N.getKey())));
        } catch (Exception e) {
            log.error(">>>> EncryptionService::bcryptEncrypt: ", e);
            throw e;
        }
    }

    public ApiResponse<String> base64Encode(Base64RequestDTO dto) {

        try {
            String encodeStr = Base64Util.encode(dto.getTargetText());

            return ApiResponse.success(msgUtil.getMessage(CommonMsgKey.SUCCESS.getKey()), encodeStr);
        } catch (Exception e) {
            log.error(">>>> EncryptionService::base64Encode: ", e);
            throw e;
        }
    }

    public ApiResponse<String> base64Decode(Base64RequestDTO dto) {
        
        try {
            String decodeStr = Base64Util.decode(dto.getTargetText());

            return ApiResponse.success(msgUtil.getMessage(CommonMsgKey.SUCCESS.getKey()), decodeStr);
        } catch (IllegalArgumentException e) {
            log.error(">>>> EncryptionService::base64Decode: ", msgUtil.getMessage(EncryptionMsgKey.FAILED_DECODE.getKey(), "base64"));
            throw new CustomException(msgUtil.getMessage(EncryptionMsgKey.FAILED_BASE64_DECODE.getKey()), HttpStatus.BAD_REQUEST, e);
        } catch (Exception e) {
            log.error(">>>> EncryptionService::base64Decode: ", e);
            throw new CustomException(msgUtil.getMessage(CommonMsgKey.FAILED.getKey()), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public ApiResponse<String> hashEncrypt(HashRequestDTO dto) {

        try {
            String encryptStr = HashUtil.encrypt(dto.getTargetText(), dto.getAlgorithm().getAlgorithm());

            return ApiResponse.success(msgUtil.getMessage(CommonMsgKey.SUCCESS.getKey()), encryptStr);
        } catch (IllegalArgumentException e) {
            log.error(">>>> EncryptionService::hashEncrypt: ", msgUtil.getMessage(EncryptionMsgKey.FAILED_ENCRYPT.getKey(), "hash"));
            throw new CustomException(msgUtil.getMessage(EncryptionMsgKey.FAILED_HASH_ENCRYPT.getKey()), HttpStatus.BAD_REQUEST, e);
        } catch (Exception e) {
            log.error(">>>> EncryptionService::hashEncrypt: ", e);
            throw new CustomException(msgUtil.getMessage(CommonMsgKey.FAILED.getKey()), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public ApiResponse<Void> hashMatches(HashRequestDTO dto) {

        try {
            
            return ApiResponse.success(msgUtil.getMessage((HashUtil.matches(dto.getTargetText(), dto.getTargetHash(), dto.getAlgorithm().getAlgorithm()) ? EncryptionMsgKey.SUCCESS_MATCHES_Y.getKey() : EncryptionMsgKey.SUCCESS_MATCHES_N.getKey())));
        } catch (IllegalArgumentException e) {
            log.error(">>>> EncryptionService::hashMatches: ", msgUtil.getMessage(EncryptionMsgKey.FAILED_ENCRYPT.getKey(), "hash"));
            throw new CustomException(msgUtil.getMessage(EncryptionMsgKey.FAILED_HASH_ENCRYPT.getKey()), HttpStatus.BAD_REQUEST, e);
        } catch (Exception e) {
            log.error(">>>> EncryptionService::hashMatches: ", e);
            throw new CustomException(msgUtil.getMessage(CommonMsgKey.FAILED.getKey()), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public ApiResponse<String> rsaEncrypt(RSARequestDTO dto) {

        try {
            
            String encryptStr = RSAUtil.encrypt(dto.getTargetText(), dto.getSecretKey());

            return ApiResponse.success(msgUtil.getMessage(CommonMsgKey.SUCCESS.getKey()), encryptStr);
        } catch (IllegalArgumentException | InvalidKeySpecException i) {
            log.error(">>>> EncryptionService::rsaEncrypt: ", msgUtil.getMessage(EncryptionMsgKey.FAILED_ENCRYPT.getKey(), "RSA"));
            throw new CustomException(msgUtil.getMessage(EncryptionMsgKey.FAILED_RSA_ENCRYPT.getKey()), HttpStatus.BAD_REQUEST, i);
        } catch (Exception e) {
            log.error(">>>> EncryptionService::rsaEncrypt: ", e);
            throw new CustomException(msgUtil.getMessage(CommonMsgKey.FAILED.getKey()), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public ApiResponse<String> rsaDecrypt(RSARequestDTO dto) {

        try {
            String decryptStr = RSAUtil.decrypt(dto.getTargetText(), dto.getSecretKey());

            return ApiResponse.success(msgUtil.getMessage(CommonMsgKey.SUCCESS.getKey()), decryptStr);
        } catch (InvalidKeySpecException e) {
            log.error(">>>> EncryptionService::rsaEncrypt: ", msgUtil.getMessage(EncryptionMsgKey.FAILED_DECRYPT.getKey(), "RSA"));
            throw new CustomException(msgUtil.getMessage(EncryptionMsgKey.FAILED_RSA_DECRYPT.getKey()), HttpStatus.BAD_REQUEST, e);
        } catch (Exception e) {
            log.error(">>>> EncryptionService::rsaDecrypt: ", e);
            throw new CustomException(msgUtil.getMessage(CommonMsgKey.FAILED.getKey()), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }
}
