package com.project.demo.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonMsgKey {

    SUCCUESS("success.basic"),
    FAILED("error.basic"),
    FAILED_VALIDATION("error.validation"),
    FAILED_DUPLICATION("error.duplication");

    private final String key;
}
