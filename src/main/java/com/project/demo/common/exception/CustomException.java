package com.project.demo.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final HttpStatus status;

    public CustomException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public CustomException(String message, HttpStatus status, Throwable cause) {
        super(message, cause); // 원인 예외 포함
        this.status = status;
    }
}
