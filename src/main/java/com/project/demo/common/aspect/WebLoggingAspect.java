package com.project.demo.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class WebLoggingAspect {

    @Pointcut("execution(* com.project.demo.web..*Controller.*(..))")
    public void webRequestLog() {}

    @Before("webRequestLog()")
    public void logWebRequest(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        if ( log.isInfoEnabled() ) {
            log.info("[WEB] ==================================================");
            log.info("[WEB] URI    : {}", request.getRequestURI());
            log.info("[WEB] Method : {}", joinPoint.getSignature().getName());
            log.info("[WEB] ==================================================");
        }
    }
}
