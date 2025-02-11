package com.project.demo.api.user.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActiveYn {

    Y("정상", true), N("탈퇴", false);

    private final String description;
    private final boolean flag;
}
