package com.project.demo.api.menu.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActiveYn {

    Y("활성화", true), N("비활성화", false);

    private final String description;
    private final boolean flag;
}
