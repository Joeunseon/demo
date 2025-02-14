package com.project.demo.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchCondition {

    ALL("전체"),
    WRITER("작성자"),
    TITLE("제목"),
    CONTENT("내용");

    private final String description;
}
