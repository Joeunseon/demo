package com.project.demo.common.aspect;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;

import com.project.demo.common.BaseDTO;
import com.project.demo.common.constant.CommonMsgKey;
import com.project.demo.common.constant.CommonConstant.RESULT;
import com.project.demo.common.constant.CommonConstant.SESSION_KEY;
import com.project.demo.common.util.CommonUtil;
import com.project.demo.common.util.MsgUtil;
import com.project.demo.config.security.application.dto.UserSessionDTO;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class CommonAspect {

    private CommonUtil commonUtil;
    private MsgUtil msgUtil;

    /**
     * 로딩할 자바스크립트 명을 설정
     * @param joinPoint
     * @param viewName
     */
    @AfterReturning(value = "execution(public String com.project.demo.web..*Controller.*(..))", returning = "viewName")
    public void afterReturning(JoinPoint joinPoint, String viewName) {
        if ( viewName == null || !StringUtils.hasText(viewName) )
            return;

        // 제외 항목 확인
        if (isExcludedRequest(joinPoint, viewName)) {
            return;
        }

        ModelMap map = getModelMapFromArgs(joinPoint.getArgs());
        if ( map == null ) {
            return;
        }

        String jsName = constructJsName(viewName);
        String jsObjName = constructJsObjName(viewName);

        map.put("jsName", jsName);
        map.put("jsObjName", jsObjName);

        log.info("jsName    " + jsName);
        log.info("jsObjName " + jsObjName);
    }

    /**
     * API 요청 또는 /error/~ 페이지 요청인지 확인
     * @param joinPoint
     * @param viewName
     * @return true: 제외 대상 요청, false: 적용 대상 요청
     */
    public boolean isExcludedRequest(JoinPoint joinPoint, String viewName) {
        // 메서드 시그니처 확인
        String methodSignature = joinPoint.getSignature().toString();

        // API 요청(`.api.` 포함) 또는 `/error/`로 시작하는 경우 제외
        return methodSignature.contains(".api.") || viewName.startsWith("/error/");
    }

    /**
     * JoinPoint에서 ModelMap 객체를 추출
     * @param args
     * @return ModelMap
     */
    private ModelMap getModelMapFromArgs(Object[] args) {
        for ( Object obj : args ) {
            if ( obj instanceof ModelMap ) {
                return (ModelMap) obj;
            }
        }
        return null;
    }

    /**
     * viewName을 바탕으로 JavaScript 파일 경로 생성
     * @param viewName
     * @return JavaScript 파일 경로
     */
    private String constructJsName(String viewName) {
        if (!viewName.startsWith("/")) {
            viewName = "/" + viewName;
        }
        return "/js" + viewName + ".js";
    }

    /**
     * viewName을 바탕으로 JavaScript 객체 이름 생성
     * @param viewName
     * @return JavaScript 객체 이름
     */
    private String constructJsObjName(String viewName) {
        String jsObjName = viewName.contains("/") ?
                viewName.substring(viewName.lastIndexOf("/") + 1) :
                viewName;

        if (jsObjName != null && jsObjName.length() > 0)
            jsObjName = StringUtils.capitalize(jsObjName);
        
        return jsObjName;
    }

    /**
     * 사용자 세션을 BaseDTO에 설정
     * @param ProceedingJoinPoint
     * @return Object
     * @throws Throwable
     */
    @Around("execution(public * com.project.demo..*Controller.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        
        HttpServletRequest request = null;
        BaseDTO baseDTO = null;
        UserSessionDTO userSessionDTO = null;

        for ( Object obj : pjp.getArgs() ) {
            log.info(">>> " + obj);
            if ( obj instanceof HttpServletRequest ) {
                request = (HttpServletRequest) obj;
                commonUtil.getRequestParmeter(request);

                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put(RESULT.RESULT, RESULT.OK);
                dataMap.put(RESULT.MSG, msgUtil.getMessage(CommonMsgKey.SUCCUESS.getKey(), request.getRequestURI()));

            } else if ( obj instanceof BaseDTO ) {
                baseDTO = (BaseDTO) obj;
            }
        }

        /** HttpServletRequest 분기 */
        if ( request != null ) {
            /** 사용자 세션 취득 */
            log.info(">>> userDTO 취득");
            userSessionDTO = (UserSessionDTO) request.getSession().getAttribute(SESSION_KEY.FRONT);
        }

        if ( userSessionDTO != null && baseDTO != null ) {
            log.info(">>> 사용자 세션 정보 설정: {}", userSessionDTO);
            /** 설정: 사용자 세션 */
            baseDTO.setUserSession(userSessionDTO);
        }

        return pjp.proceed();
    }
}
