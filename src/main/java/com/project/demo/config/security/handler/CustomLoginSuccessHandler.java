package com.project.demo.config.security.handler;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.project.demo.common.constant.Login;
import com.project.demo.common.util.MsgUtil;
import com.project.demo.config.security.application.dto.CustomUserDetails;
import com.project.demo.config.security.application.dto.UserSessionDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final MsgUtil msgUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("onAuthenticationSuccess");

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        HttpSession session = request.getSession();
        session.setAttribute("user", UserSessionDTO.of(userDetails.toEntity()));

        log.info(msgUtil.getMessage(Login.SUCCUESS.getKey(), userDetails.getUserNm()));
        
        /** 비밀번호 변경 필요 여부 확인(3개월 후) */
        if ( userDetails.getLastPwdDt() == null || userDetails.getLastPwdDt().isBefore(LocalDateTime.now().minusMonths(3)) ) {
            response.sendRedirect("/user/password");
        } else {
            response.sendRedirect("/");
        }
    }
}
