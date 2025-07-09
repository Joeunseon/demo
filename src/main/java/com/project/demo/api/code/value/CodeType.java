package com.project.demo.api.code.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CodeType {

    GRP("그룹 코드"), CD("코드");

    private final String description;
}
