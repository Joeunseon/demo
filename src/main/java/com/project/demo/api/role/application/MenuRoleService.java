package com.project.demo.api.role.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;

import com.project.demo.api.menu.value.ActiveYn;
import com.project.demo.api.role.infrastructure.MenuRoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuRoleService {

    private final MenuRoleRepository menuRoleRepository;
    private final AntPathMatcher pathMatcher = new AntPathMatcher(); // URL 패턴 매칭

    @Transactional(readOnly = true)
    public boolean hasAccess(Long roleSeq, String url) {
        
        List<String> menuUrlPatterns = menuRoleRepository.findByRole_RoleSeqAndMenu_ActiveYn(roleSeq, ActiveYn.Y)
                                        .stream()
                                        .map(menuRole -> menuRole.getMenu().getMenuUrl())
                                        .distinct()
                                        .collect(Collectors.toList());

        return menuUrlPatterns.stream().anyMatch(pattern -> pathMatcher.match(pattern, url));
    }
}
