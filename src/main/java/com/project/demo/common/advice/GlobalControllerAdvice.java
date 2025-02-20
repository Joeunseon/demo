package com.project.demo.common.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.project.demo.api.role.application.MenuRoleService;
import com.project.demo.common.constant.CommonConstant.SESSION_KEY;
import com.project.demo.config.security.application.dto.UserSessionDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalControllerAdvice {

    private final MenuRoleService menuRoleService;

    private static final Integer GEUST_ROLE = 4;

    @ModelAttribute
    public void addGlobalAttributes(Model model, HttpServletRequest request) {

        String requestURI = request.getRequestURI();
        // /error, /api 요청 확인
        if ( requestURI.startsWith("/error") || requestURI.startsWith("/api") ) {
            return;
        }

        HttpSession session = request.getSession(false);
        UserSessionDTO userSessionDTO = (session != null) ? (UserSessionDTO) session.getAttribute(SESSION_KEY.FRONT) : null;

        // 메뉴 리스트 추가 예정

        if ( userSessionDTO != null ) {
            model.addAttribute("loginInfo", userSessionDTO);
        } 
    }
}
