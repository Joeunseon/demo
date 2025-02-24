package com.project.demo.config.security.handler;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.project.demo.common.constant.LoginMsgKey;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("onAuthenticationFailure");

        String msgKey;
        /** 로그인 실패 분기 */
        if ( exception instanceof UsernameNotFoundException ) {
            msgKey = LoginMsgKey.USER_NOT_FOUND.getKey();
        } else if ( exception instanceof BadCredentialsException ) {
            msgKey = LoginMsgKey.BAD_CREDENTIALS.getKey();
        } else {
            msgKey = LoginMsgKey.FAILED.getKey();
        }

        response.sendRedirect("/login?msgKey=" + msgKey);
    }
}
