package com.project.demo.config.security.handler;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.project.demo.api.user.application.UserService;
import com.project.demo.common.constant.LoginMsgKey;
import com.project.demo.common.constant.CommonConstant.EXCLUDE_URL;
import com.project.demo.common.constant.CommonConstant.MODEL_KEY;
import com.project.demo.common.constant.CommonConstant.SESSION_KEY;
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

    private final UserService userService;
    private final MsgUtil msgUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("onAuthenticationSuccess");

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        HttpSession session = request.getSession();
        session.setAttribute(SESSION_KEY.FRONT, UserSessionDTO.of(userDetails.toEntity()));

        log.info(msgUtil.getMessage(LoginMsgKey.SUCCUESS.getKey(), userDetails.getUserNm()));

        // 마지막 로그인시간 업데이트
        userService.updateLastLoginDt(userDetails.getUserSeq());
        
        /** 비밀번호 변경 필요 여부 확인(3개월 후) */
        if ( userDetails.getLastPwdDt() == null || userDetails.getLastPwdDt().isBefore(LocalDateTime.now().minusMonths(3)) ) {
            response.sendRedirect("/user/password");
        } else {
            String redirectUrl = (String) request.getParameter(MODEL_KEY.REDIRECT_URL);

            // redirectUrl (로그인, 회원가입 페이지 제외)
            if ( StringUtils.hasText(redirectUrl) && !redirectUrl.startsWith(EXCLUDE_URL.LOGIN) && !redirectUrl.startsWith(EXCLUDE_URL.SIGNUP) ) {
                response.sendRedirect(redirectUrl);
            } else {
                response.sendRedirect("/");
            }
        }
    }
}
