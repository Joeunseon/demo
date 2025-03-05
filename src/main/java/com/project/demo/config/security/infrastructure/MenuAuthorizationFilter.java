package com.project.demo.config.security.infrastructure;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.demo.api.menu.application.MenuService;
import com.project.demo.api.role.application.MenuRoleService;
import com.project.demo.common.constant.CommonConstant.EXCLUDE_URL;
import com.project.demo.common.constant.CommonConstant.ROLE_KEY;
import com.project.demo.common.constant.CommonConstant.SESSION_KEY;
import com.project.demo.config.security.application.dto.UserSessionDTO;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class MenuAuthorizationFilter extends OncePerRequestFilter {

    private final MenuService menuService;
    private final MenuRoleService menuRoleService;

    private static final List<String> STATIC_RESOURCES = List.of("/css/**", "/images/**", "/js/**");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        String requestMethod = request.getMethod();

        // 정적 리소스 URL은 필터링 제외
        if (isStaticResource(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession(false);
        UserSessionDTO userSessionDTO = (session != null) ? (UserSessionDTO) session.getAttribute(SESSION_KEY.FRONT) : null;

        // /error URL은 검증 하지 않음
        if ( !requestURI.startsWith(EXCLUDE_URL.ERROR) ) {
            // 메뉴 존재 여부 확인
            if ( !menuService.isMenuExist(requestURI) ) {
                log.warn(">>> 존재하지 않는 URL 요청: {}", requestURI);
                response.sendRedirect("/error/404");
                return;
            }

            Integer roleSeq = Optional.ofNullable(userSessionDTO)
                                .map(UserSessionDTO::getRoleSeq)
                                .orElse(ROLE_KEY.GUEST);

            // 권한 확인
            if ( !menuRoleService.hasAccess(roleSeq, requestURI, requestMethod) ) {
                log.warn(">>> 접근 불가 (권한 없음): {} (roleSeq={})", requestURI, roleSeq);
                response.sendRedirect("/error/403");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private boolean isStaticResource(String requestURI) {
        return STATIC_RESOURCES.stream().anyMatch(pattern -> requestURI.startsWith(pattern.replace("**", "")));
    }
}
