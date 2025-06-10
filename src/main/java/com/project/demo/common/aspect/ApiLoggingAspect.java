package com.project.demo.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.project.demo.common.constant.CommonConstant.SESSION_KEY;
import com.project.demo.config.security.application.dto.UserSessionDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class ApiLoggingAspect {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Pointcut("execution(* com.project.demo.api..*Controller.*(..))")
    public void apiRequestLog() {}

    @Before("apiRequestLog()")
    public void logRequestInfo(JoinPoint joinPoint) {
        if ( !"local".equals(activeProfile) && !"dev".equals(activeProfile) && !log.isInfoEnabled() ) {
            return; // local, dev 환경이 아니고, info도 아니면 생략
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        HttpSession session = request.getSession(false);
        UserSessionDTO userSessionDTO = (session != null) ? (UserSessionDTO) session.getAttribute(SESSION_KEY.FRONT) : null;
        String userId = (userSessionDTO != null ? userSessionDTO.getUserId() : "anonymous");

        log.info("[API] ==================================================");
        log.info("[API] URI           : {}", request.getRequestURI());
        log.info("[API] Class         : {}", joinPoint.getTarget().getClass().getSimpleName());
        log.info("[API] Method        : {}", joinPoint.getSignature().getName());
        log.info("[API] Remote IP     : {}", request.getRemoteAddr());
        log.info("[API] User ID       : {}", userId);
        log.info("[API] ==================================================");
    }
}
