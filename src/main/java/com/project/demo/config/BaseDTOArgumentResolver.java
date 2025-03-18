package com.project.demo.config;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.project.demo.common.BaseDTO;
import com.project.demo.common.constant.CommonConstant.SESSION_KEY;
import com.project.demo.config.security.application.dto.UserSessionDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BaseDTOArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(BaseDTO.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);
        UserSessionDTO userSessionDTO = (session != null) ? 
            (UserSessionDTO) session.getAttribute(SESSION_KEY.FRONT) : null;

        BaseDTO baseDTO = new BaseDTO();
        if (userSessionDTO != null) {
            baseDTO.setUserSession(userSessionDTO);
        } else {
            return null;
        }

        return baseDTO;  // 컨트롤러에서 자동으로 BaseDTO 생성
    }
}
