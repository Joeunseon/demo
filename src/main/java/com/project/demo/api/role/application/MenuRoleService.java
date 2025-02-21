package com.project.demo.api.role.application;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;

import com.project.demo.api.menu.application.dto.MenuDisplayDTO;
import com.project.demo.api.menu.value.ActiveYn;
import com.project.demo.api.role.infrastructure.MenuRoleRepository;
import com.project.demo.common.constant.CommonConstant.EXCLUDE_URL;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuRoleService {

    private final MenuRoleRepository menuRoleRepository;
    private final AntPathMatcher pathMatcher = new AntPathMatcher(); // URL 패턴 매칭

    @Transactional(readOnly = true)
    public boolean hasAccess(Integer roleSeq, String url) {
        
        List<String> menuUrlPatterns = menuRoleRepository.findByRole_RoleSeqAndMenu_ActiveYn(roleSeq, ActiveYn.Y)
                                        .stream()
                                        .map(menuRole -> menuRole.getMenu().getMenuUrl())
                                        .filter(Objects::nonNull)
                                        .distinct()
                                        .collect(Collectors.toList());

        return menuUrlPatterns.stream().anyMatch(pattern -> pathMatcher.match(pattern, url));
    }

    public List<MenuDisplayDTO> getDisplayMenus(Integer roleSeq) {

        return menuRoleRepository.findDisplayMenus(roleSeq).stream()
                .filter(menuDisplay -> !menuDisplay.getMenuUrl().startsWith(EXCLUDE_URL.LOGIN))
                .filter(menuDisplay -> !menuDisplay.getMenuUrl().startsWith(EXCLUDE_URL.JOIN))
                .distinct()
                .collect(Collectors.toList());
    }
}
