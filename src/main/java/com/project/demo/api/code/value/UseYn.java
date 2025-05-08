package com.project.demo.api.code.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UseYn {

    Y("사용"), N("미사용");

    private final String description;
}
