package com.project.demo.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JoinMsgKey {

    SUCCUESS_ID("success.join.id"),
    SUCCUESS_EMAIL("success.join.email"),
    FAILED_ID("error.join.id"),
    ID_PATTERN("error.join.id_pattern"),
    ID_FORBIDDEN_WORDS("error.join.id_forbidden_words"),
    PWD_PATTERN("error.join.pwd_pattern"),
    PWD_CONTAINS_ID("error.join.pwd_contains_id"),
    PWD_BAD_CREDENTIALS("error.join.pwd_bad_credentials"),
    FAILED_EMAIL("error.join.email"),
    EMAIL_PATTERN("error.join.email_pattern");

    private final String key;
}
