package com.project.demo.api.log.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResolvedStat {

    ALL("전체"),
    Y("해결"),
    N("미해결");

    private final String description;
}
