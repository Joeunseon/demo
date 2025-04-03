package com.project.demo.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MenuMsgKey {

    SUCCUESS_DUPLICATION("success.menu.duplication"),
    FAILED_DUPLICATION("error.menu.duplication");

    private final String key;
}
