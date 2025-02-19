package com.project.demo.api.log.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrLevel {

    INFO("정보"), WARN("경고"), ERROR("오류"), CRITICAL("심각");

    private final String description;
}
