package com.project.demo.config.security.infrastructure;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.project.demo.api.role.application.MenuRoleService;
import com.project.demo.common.advice.GlobalControllerAdvice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PermitAllCacheRefresher {

    private final MenuRoleService menuRoleService;

    private final GlobalControllerAdvice globalControllerAdvice;

    @Scheduled(fixedRate = 600000)
    public void refreshPermitAllUrls() {
        //log.info("GUEST ROLE 접근 가능 URL 캐시 갱신");
        menuRoleService.refreshGuestAccessibleUrls();
        globalControllerAdvice.evictGuestMenuCache();
    }
}
