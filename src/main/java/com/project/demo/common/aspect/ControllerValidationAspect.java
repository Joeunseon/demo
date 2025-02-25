package com.project.demo.common.aspect;

import java.lang.reflect.Method;
import java.util.Set;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@RequiredArgsConstructor
@Order(1)
@Slf4j
public class ControllerValidationAspect {

    private final Validator validator;

    @Before("execution(public * com.project.demo.api..*Controller.*(..))")
    public void validateControllerParameters(JoinPoint joinPoint) {
        
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        boolean isValidated = method.isAnnotationPresent(Validated.class) ||
                                method.getDeclaringClass().isAnnotationPresent(Validated.class);
        
        // @Validated 어노테이션 유무 체크
        if ( !isValidated )
            return;
        
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            // null인 경우 스킵
            if ( arg == null ) continue;

            // Bean Validation API를 통해 유효성 검사
            Set<ConstraintViolation<Object>> violations = validator.validate(arg);
            if ( !violations.isEmpty() ) {
                ConstraintViolation<Object> violation = violations.iterator().next();

                log.info(">>>> 유효성 검사 실패: ", violation.getMessage());
                // 유효성 검사 실패 시 예외 발생
                throw new ValidationException(violation.getMessage());
            }
        }
    }
}
