package com.project.demo.common.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.project.demo.api.log.domain.ErrLogEntity;
import com.project.demo.api.log.infrastructure.ErrLogRepository;
import com.project.demo.api.log.value.ErrLevel;
import com.project.demo.common.constant.CommonConstant.SESSION_KEY;
import com.project.demo.config.security.application.dto.UserSessionDTO;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice(basePackages = "com.project.demo.web")
@RequiredArgsConstructor
@Slf4j
public class GlobalViewExceptionHandler {

    private final ErrLogRepository errLogRepository;

    /**
     * 모든 예외 처리
     * @param ex
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ExceptionHandler(Exception.class)
    public String handleViewException(Exception ex, HttpServletRequest request, ModelMap model) {

        HttpStatus status = getHttpStatus(ex);

        // 로그 저장
        logErrorToDatabase(ex, request, status);

        // 사용자에게 보여줄 메시지 설정
        model.addAttribute("errorMessage", ex.getMessage());

        // 상태 코드별로 분기 가능
        if (status == HttpStatus.NOT_FOUND) {
            return "/error/404";
        } else if (status == HttpStatus.FORBIDDEN) {
            return "/error/403";
        } else if ( status == HttpStatus.INTERNAL_SERVER_ERROR ) {
            return "/error/500";
        }

        return "/error/default";
    }

    /**
     * 공통 에러 로그 저장 로직
     * @param ex
     * @param request
     * @param status
     */
    private void logErrorToDatabase(Exception ex, HttpServletRequest request, HttpStatus status) {
        // 스택 트레이스 변환
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        String stackTrace = sw.toString();

        // 로그인 사용자 userSeq 취득
        Long userSeq = null;
        UserSessionDTO userSessionDTO = (UserSessionDTO) request.getSession().getAttribute(SESSION_KEY.FRONT);
        if ( userSessionDTO != null )
            userSeq = userSessionDTO.getUserSeq();

        ErrLogEntity logEntity = ErrLogEntity.builder()
                .errCd(String.valueOf(status.value()))
                .errMsg(ex.getMessage())
                .stackTrace(stackTrace)
                .errLevel(determineErrorLevel(status))
                .occurredDt(LocalDateTime.now())
                .requestUrl(request.getRequestURI())
                .requestMethod(request.getMethod())
                .requestSeq(userSeq)
                .build();

        // err log 저장
        errLogRepository.save(logEntity);
    }

    /**
     * 예외에서 HTTP 상태 코드 추출
     * @param ex
     * @return
     */
    private HttpStatus getHttpStatus(Exception ex) {
        ResponseStatus status = ex.getClass().getAnnotation(ResponseStatus.class);

        if (status != null) 
            return status.value();

        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    /**
     * HTTP 상태 코드에 따른 에러 레벨 분류
     * @param status
     * @return
     */
    public ErrLevel determineErrorLevel(HttpStatus status) {
        if ( status.is5xxServerError() ) {
            return ErrLevel.CRITICAL;
        } else if ( status == HttpStatus.BAD_REQUEST || status == HttpStatus.UNAUTHORIZED || status == HttpStatus.FORBIDDEN ) {
            return ErrLevel.WARN;
        } else if ( status == HttpStatus.NOT_FOUND ) {
            return ErrLevel.INFO;
        }

        return ErrLevel.ERROR;
    }
}
