package com.project.demo.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EncryptionMsgKey {

    SUCCUESS_MATCHES_Y("success.encrypt.matches_y"),
    SUCCUESS_MATCHES_N("success.encrypt.matches_n"),
    FAILED_DECRYPT("error.encrypt.failed_decrypt"),
    FAILED_JASYPT_DECRYPT("error.encrypt.jasypt_decrypt");

    private final String key;
}
