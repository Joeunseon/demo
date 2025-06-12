package com.project.demo.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EncryptionMsgKey {

    SUCCESS_MATCHES_Y("success.encrypt.matches_y"),
    SUCCESS_MATCHES_N("success.encrypt.matches_n"),
    FAILED_DECRYPT("error.encrypt.failed_decrypt"),
    FAILED_JASYPT_DECRYPT("error.encrypt.jasypt_decrypt"),
    FAILED_DECODE("error.encrypt.failed_decode"),
    FAILED_BASE64_DECODE("eroor.encrypt.base64_decrypt"),
    FAILED_ENCRYPT("error.encrypt.failed_encrypt"),
    FAILED_HASH_ENCRYPT("error.encrypt.hash_encrypt"),
    FAILED_RSA_ENCRYPT("error.encrypt.rsa_encrypt"),
    FAILED_RSA_DECRYPT("error.encrypt.rsa_decrypt");

    private final String key;
}
