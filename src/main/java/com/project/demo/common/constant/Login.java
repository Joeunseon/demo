package com.project.demo.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Login {

    SUCCUESS("success.login"),
    FAILED("error.login.failed"),
    USER_NOT_FOUND("error.login.user_not_found"),
    BAD_CREDENTIALS("error.login.bad_credentials");

    private final String key;
}
