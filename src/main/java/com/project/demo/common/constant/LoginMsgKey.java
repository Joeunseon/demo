package com.project.demo.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoginMsgKey {

    SUCCUESS("success.login"),
    SUCCUESS_LAST_LOGIN("success.login.last_login_dt"),
    FAILED("error.login.failed"),
    USER_NOT_FOUND("error.login.user_not_found"),
    BAD_CREDENTIALS("error.login.bad_credentials"),
    FAILED_LAST_LOGIN("error.login.last_login_dt");

    private final String key;
}
