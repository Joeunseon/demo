package com.project.demo.common.advice;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.demo.common.BaseDTO;
import com.project.demo.common.constant.CommonConstant.SESSION_KEY;
import com.project.demo.config.security.application.dto.UserSessionDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalRestControllerAdvice {

    @InitBinder
    public void initBinder(WebDataBinder binder, HttpServletRequest request) {
        Object target = binder.getTarget();

        if ( target != null && target.getClass() != BaseDTO.class && BaseDTO.class.isAssignableFrom(target.getClass()) ) {
            BaseDTO baseDTO = (BaseDTO) target;

            HttpSession session = request.getSession(false);
            UserSessionDTO userSessionDTO = (session != null) ? 
                (UserSessionDTO) session.getAttribute(SESSION_KEY.FRONT) : null;

            if (userSessionDTO != null) {
                baseDTO.setUserSession(userSessionDTO);
                log.info(">>> 사용자 세션 정보 설정 완료: {}", userSessionDTO);
            }
        }
    }
}
