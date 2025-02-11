package com.project.demo.config.security.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.project.demo.api.user.domain.UserEntity;
import com.project.demo.api.user.infrastructure.UserRepository;
import com.project.demo.api.user.value.ActiveYn;
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

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        UserEntity user = userRepository.findByUserIdAndActiveYn(userDetails.getUsername(), ActiveYn.Y)
                            .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        HttpSession session = request.getSession();
        session.setAttribute("user", UserSessionDTO.of(user));

        log.info("로그인 성공");

        response.sendRedirect("/");
    }
}
