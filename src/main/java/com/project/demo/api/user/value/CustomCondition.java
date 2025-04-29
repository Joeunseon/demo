package com.project.demo.api.user.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomCondition {

    ALL("전체"),
    ID("ID"),
    NAME("성명");

    private final String description;
}
