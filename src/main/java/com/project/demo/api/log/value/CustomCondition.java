package com.project.demo.api.log.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomCondition {

    ALL("전체"),
    MSG("메시지"),
    URL("발생 URL");

    private final String description;
}
