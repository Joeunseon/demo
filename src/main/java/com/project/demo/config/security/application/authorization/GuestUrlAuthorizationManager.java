package com.project.demo.config.security.application.authorization;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.project.demo.api.role.application.MenuRoleService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GuestUrlAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final MenuRoleService menuRoleService;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        HttpServletRequest request = context.getRequest();
        String requestURI = request.getRequestURI();

        List<String> guestUrls = menuRoleService.getGuestAccessibleUrls();

        boolean isGuestUrl = guestUrls.stream()
                                        .filter(StringUtils::hasText)
                                        .anyMatch(pattern -> requestURI.matches(convertToRegex(pattern)));

        Authentication auth = authentication.get();

        // 인증되지 않은 사용자만 guest URL 접근 허용
        boolean isGuest = !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken;

        if (isGuest && isGuestUrl) {
            return new AuthorizationDecision(true);
        }

        // 인증된 사용자는 통과 (인증 여부만 확인)
        if (auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return new AuthorizationDecision(true); // 로그인 사용자 → 통과
        }

        return new AuthorizationDecision(false); // 나머지는 차단
    }

    private String convertToRegex(String pattern) {
        // Spring URL 패턴을 정규식으로 변환
        String regex = pattern
                .replace(".", "\\.")
                .replace("**", ".*")
                .replace("*", "[^/]*");
        return "^" + regex + "$";
    }
}
