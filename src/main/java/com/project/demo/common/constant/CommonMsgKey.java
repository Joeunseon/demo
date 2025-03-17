package com.project.demo.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonMsgKey {

    SUCCUESS("success.basic"),
    FAILED("error.basic"),
    FAILED_VALIDATION("error.validation"),
    FAILED_LENGTH("error.length"),
    FAILED_DUPLICATION("error.duplication"),
    FAILED_FORBIDDEN("error.forbidden"),
    FAILED_SEARCH_DATE("error.search_date"),
    FAILED_REQUEST("error.request");

    private final String key;
}
