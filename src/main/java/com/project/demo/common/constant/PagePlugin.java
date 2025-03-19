package com.project.demo.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PagePlugin {

    EDITOR("TOAST"), FILE("DROPZONE");

    private final String description;

}
