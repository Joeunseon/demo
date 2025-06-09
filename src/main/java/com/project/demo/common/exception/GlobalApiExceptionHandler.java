package com.project.demo.common.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.project.demo.api.log.domain.ErrLogEntity;
import com.project.demo.api.log.infrastructure.ErrLogRepository;
import com.project.demo.api.log.value.ErrLevel;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.constant.CommonConstant.SESSION_KEY;
import com.project.demo.config.security.application.dto.UserSessionDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice(basePackages = "com.project.demo.api")
@RequiredArgsConstructor
@Slf4j
public class GlobalApiExceptionHandler {

    private final ErrLogRepository errLogRepository;

    /**
     * 유효성 검증 실패 (ValidationException) 처리
     * @param ex
     * @return
     */
    @ExceptionHandler(ValidationException.class)
    public ApiResponse<?> handleValidationException(ValidationException ex) {

        return ApiResponse.error(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    /**
     * 모든 예외 처리 (가장 마지막 핸들러)
     * @param ex
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handlerException(Exception ex, WebRequest request, HttpServletRequest httpServletRequest) {
        
        // HTTP 상태 코드 취득
        HttpStatus status = getHttpStatus(ex);
        
        log.error("예외 발생: ", ex);

        logErrorToDatabase(ex, httpServletRequest, status);

        return ApiResponse.error(status, ex.getMessage());
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

        ErrLogEntity errLog = ErrLogEntity.builder()
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
        errLogRepository.save(errLog);
    }

    /**
     * 예외에서 HTTP 상태 코드 추출
     * @param ex
     * @return
     */
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
