package com.project.demo.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EncryptionMsgKey {

    FAILED_DECRYPT("error.encrypt.failed_decrypt"),
    FAILED_JASYPT_DECRYPT("error.encrypt.jasypt_decrypt");

    private final String key;
}
