package com.project.demo.api.log.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RequestMethod {

    ALL("전체"),
    GET("GET"),
    POST("POST"),
    PATCH("PATCH"),
    DELETE("DELETE");

    private final String description;
}
