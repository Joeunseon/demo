package com.project.demo.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SignupMsgKey {

    SUCCESS("success.signup.register"),
    SUCCUESS_ID("success.signup.id"),
    SUCCUESS_EMAIL("success.signup.email"),
    FAILED_ID("error.signup.id"),
    ID_PATTERN("error.signup.id_pattern"),
    ID_FORBIDDEN_WORDS("error.signup.id_forbidden_words"),
    PWD_PATTERN("error.signup.pwd_pattern"),
    PWD_CONTAINS_ID("error.signup.pwd_contains_id"),
    PWD_BAD_CREDENTIALS("error.signup.pwd_bad_credentials"),
    FAILED_EMAIL("error.signup.email"),
    EMAIL_PATTERN("error.signup.email_pattern");

    private final String key;
}
