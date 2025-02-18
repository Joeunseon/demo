package com.project.demo.config.security.handler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.demo.common.constant.LoginMsgKey;
import com.project.demo.common.constant.CommonConstant.RESULT;
import com.project.demo.common.util.MsgUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

    private final MsgUtil msgUtil;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("onAuthenticationFailure");

        /** 설정 : Content-Type -> application/json */
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        /** 설정 : Character-Encoding -> UTF8 */
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        /** 출력할 Map */
        Map<String, Object> dataMap = new HashMap<>();
        /** 설정: 결과 -> ERR */
        dataMap.put(RESULT.RESULT, RESULT.ERR);
        
        /** 로그인 실패 분기 */
        if ( exception instanceof UsernameNotFoundException ) {
            dataMap.put(RESULT.MSG, msgUtil.getMessage(LoginMsgKey.USER_NOT_FOUND.getKey()));
        } else if ( exception instanceof BadCredentialsException ) {
            dataMap.put(RESULT.MSG, msgUtil.getMessage(LoginMsgKey.BAD_CREDENTIALS.getKey()));
        } else {
            dataMap.put(RESULT.MSG, msgUtil.getMessage(LoginMsgKey.FAILED.getKey()));
        }

        /** JSON 응답 출력 */
        response.getWriter().write(objectMapper.writeValueAsString(dataMap));
    }
}
