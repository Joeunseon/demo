package com.project.demo.api.menu.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MenuType {

    PAGE("페이지"), CREATE("등록"), READ("조회"), UPDATE("수정"), DELETE("삭제");

    private final String description;
}
