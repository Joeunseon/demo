package com.project.demo.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthMsgKey {

    SUCCESS_LOGIN("success.auth.login"),
    SUCCESS_LAST_LOGIN("success.auth.last_login_dt"),
    SUCCESS_LOGOUT("success.auth.logout"),
    FAILED_LOGIN("error.auth.failed"),
    USER_NOT_FOUND("error.auth.user_not_found"),
    BAD_CREDENTIALS("error.auth.bad_credentials"),
    FAILED_LAST_LOGIN("error.auth.last_login_dt");

    private final String key;
}
