package com.project.demo.api.role.application;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import com.project.demo.api.menu.application.dto.MenuDisplayDTO;
import com.project.demo.api.menu.value.ActiveYn;
import com.project.demo.api.menu.value.MenuType;
import com.project.demo.api.role.application.dto.MenuRoleDTO;
import com.project.demo.api.role.infrastructure.MenuRoleRepository;
import com.project.demo.common.constant.DelYn;
import com.project.demo.common.constant.CommonConstant.CACHE_KEY;
import com.project.demo.common.constant.CommonConstant.EXCLUDE_URL;
import com.project.demo.common.constant.CommonConstant.ROLE_KEY;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuRoleService {

    private final MenuRoleRepository menuRoleRepository;
    private final AntPathMatcher pathMatcher = new AntPathMatcher(); // URL 패턴 매칭

    @Cacheable(value = CACHE_KEY.SECURITY, key = "'guestUrls'", unless = "#result == null or #result.isEmpty()")
    public List<String> getGuestAccessibleUrls() {
        log.info(">>> DB에서 GUEST_ROLE 접근 가능 URL 조회");

        return menuRoleRepository.findByRole_RoleSeqAndMenu_ActiveYnAndDelYn(ROLE_KEY.GUEST, ActiveYn.Y, DelYn.N)
                .stream()
                .map(menuRole -> converToWildcardpattern(menuRole.getMenu().getMenuUrl()))
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    @CacheEvict(value = CACHE_KEY.SECURITY, key = "'guestUrls'")
    public void refreshGuestAccessibleUrls() {
        log.info(">>> GUEST_ROLE 접근 가능 URL 캐시 갱신");
    }

    private String converToWildcardpattern(String url) {

        return StringUtils.hasText(url) ? url.replaceAll("\\{[^}]+\\}", "*") : url;
    }

    @Transactional(readOnly = true)
    public boolean hasAccess(Integer roleSeq, String url, String method) {
        
        List<MenuRoleDTO> menuRoles = menuRoleRepository.findByRole_RoleSeqAndMenu_ActiveYnAndDelYn(roleSeq, ActiveYn.Y, DelYn.N)
                                        .stream()
                                        .map(MenuRoleDTO::new)
                                        .filter(menuRoleDTO -> Objects.nonNull(menuRoleDTO.getMenuUrl()))
                                        .distinct()
                                        .collect(Collectors.toList());

        return menuRoles.stream().anyMatch(menuRoleDTO -> 
            pathMatcher.match(menuRoleDTO.getMenuUrl(), url) && (url.contains(EXCLUDE_URL.LOGIN) || isMethodAllowed(menuRoleDTO.getMenuType(), method))
        );
    }

    public boolean isMethodAllowed(MenuType menuType, String method) {
        if ( menuType == null ) 
            return false;
        
        return switch (menuType) {
            case MENU, PAGE, READ, TOOL -> method.equalsIgnoreCase("GET");
            case UPDATE -> method.equalsIgnoreCase("PATCH");
            case CREATE -> method.equalsIgnoreCase("POST");
            case DELETE -> method.equalsIgnoreCase("DELETE");
            default -> false;
        };
    }

    public List<MenuDisplayDTO> getDisplayMenus(Integer roleSeq) {

        return menuRoleRepository.findDisplayMenus(roleSeq).stream()
                .distinct()
                .collect(Collectors.toList());
    }
}
