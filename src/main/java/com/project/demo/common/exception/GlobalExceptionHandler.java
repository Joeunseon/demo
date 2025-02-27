package com.project.demo.common.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.project.demo.api.log.domain.ErrLogEntity;
import com.project.demo.api.log.infrastructure.ErrLogRepositroy;
import com.project.demo.api.log.value.ErrLevel;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.constant.CommonConstant.SESSION_KEY;
import com.project.demo.config.security.application.dto.UserSessionDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final ErrLogRepositroy errLogRepositroy;

    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public ApiResponse<?> handleValidationException(ValidationException ex) {

        return ApiResponse.error(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handlerException(Exception ex, WebRequest request, HttpServletRequest httpServletRequest) {
        
        // HTTP 상태 코드 취득
        HttpStatus status = getHttpStatus(ex);
        
        log.error("예외 발생: ", ex);

        // 스택 트레이스 변환
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        String stackTrace = sw.toString();

        // 오류 심각도(LEVEL) 설정
        ErrLevel errLevel = determineErrorLevel(status);

        // 로그인 사용자 userSeq 취득
        Long userSeq = null;
        UserSessionDTO userSessionDTO = (UserSessionDTO) httpServletRequest.getSession().getAttribute(SESSION_KEY.FRONT);
        if (userSessionDTO != null) {
            userSeq = userSessionDTO.getUserSeq();
        }

        ErrLogEntity errLog = ErrLogEntity.builder()
                                    .errCd(String.valueOf(status.value()))
                                    .errMsg(ex.getMessage())
                                    .stackTrace(stackTrace)
                                    .errLevel(errLevel)
                                    .occurredDt(LocalDateTime.now())
                                    .requestUrl(httpServletRequest.getRequestURI())
                                    .requestMethod(httpServletRequest.getMethod())
                                    .requestSeq(userSeq)
                                    .build();

        log.info(errLog.toString());

        // err log 저장 (서버 내부 오류만 저장)
        errLogRepositroy.save(errLog);

        return ApiResponse.error(status, ex.getMessage());
    }

    private HttpStatus getHttpStatus(Exception ex) {
        // @ResponseStatus 어노테이션이 있는 경우 해당 HTTP 상태 코드 반환
        ResponseStatus status = ex.getClass().getAnnotation(ResponseStatus.class);
        if ( status != null ) {
            return status.value();
        }

        // CustomException 일 경우 상태 코드 반환
        if ( ex instanceof CustomException ) {
            return ((CustomException) ex).getStatus();
        }

        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

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
