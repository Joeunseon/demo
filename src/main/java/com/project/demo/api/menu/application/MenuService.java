package com.project.demo.api.menu.application;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import com.project.demo.api.menu.infrastructure.MenuRepository;
import com.project.demo.api.menu.value.ActiveYn;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuService {

    private final MenuRepository menuRepository;
    private final AntPathMatcher pathMatcher = new AntPathMatcher(); // URL 패턴 매칭

    public boolean isMenuExist(String url) {

        List<String> menuUrlPatterns = menuRepository.findByActiveYn(ActiveYn.Y)
                                        .stream()
                                        .map(menu -> menu.getMenuUrl())
                                        .filter(Objects::nonNull)
                                        .distinct()
                                        .collect(Collectors.toList());

        return menuUrlPatterns.stream().anyMatch(pattern -> pathMatcher.match(pattern, url));
    }
}
