package com.project.demo.common.advice;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.project.demo.api.menu.application.dto.MenuDisplayDTO;
import com.project.demo.api.role.application.MenuRoleService;
import com.project.demo.common.constant.CommonConstant.EXCLUDE_URL;
import com.project.demo.common.constant.CommonConstant.MODEL_KEY;
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
    private final Map<Integer, List<MenuDisplayDTO>> menuCache = new ConcurrentHashMap<>();

    @ModelAttribute
    public void addGlobalAttributes(Model model, HttpServletRequest request) {

        String requestURI = request.getRequestURI();
        
        // 공통 제외 URL 적용
        if ( EXCLUDE_URL.EXCLUDE_URL_LIST.stream().anyMatch(requestURI::startsWith) ) {
            return;
        }

        HttpSession session = request.getSession(false);
        UserSessionDTO userSessionDTO = (session != null) ? (UserSessionDTO) session.getAttribute(SESSION_KEY.FRONT) : null;
        Integer roleSeq = (userSessionDTO != null) ? userSessionDTO.getRoleSeq() : GEUST_ROLE;

        // 메뉴 리스트 캐싱 (같은 roleSeq에 대해 중복 조회 방지)
        menuCache.computeIfAbsent(roleSeq, menuRoleService::getDisplayMenus);
        model.addAttribute(MODEL_KEY.MENU_LIST, menuCache.get(roleSeq));

        if ( userSessionDTO != null ) {
            model.addAttribute(MODEL_KEY.LOGIN_INFO, userSessionDTO);
        }
    }
}
