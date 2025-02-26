package com.project.demo.common.aspect;

import java.lang.reflect.Method;
import java.util.HashSet;
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
import jakarta.validation.executable.ExecutableValidator;
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
        log.info(">>>> validateControllerParameters");
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Object target = joinPoint.getTarget();
        Object[] args = joinPoint.getArgs();

        boolean isValidated = method.isAnnotationPresent(Validated.class) ||
                                method.getDeclaringClass().isAnnotationPresent(Validated.class);
        
        // @Validated 어노테이션 유무 체크
        if ( !isValidated )
            return;
        
        // 1. 메서드 파라미터에 적용된 제약 조건 검증 (예: @Pattern, @Email 등)
        ExecutableValidator executableValidator = validator.forExecutables();
        Set<ConstraintViolation<Object>> parameterViolations = executableValidator.validateParameters(target, method, args);

        // 2. 각 인자(객체) 내부의 제약 조건 검증 (객체의 필드 등에 선언된 제약조건)
        Set<ConstraintViolation<Object>> beanViolations = new HashSet<>();
        for (Object arg : args) {
            if (arg == null) continue;
            beanViolations.addAll(validator.validate(arg));
        }

        // 두 검증 결과를 모두 취합
        Set<ConstraintViolation<Object>> allViolations = new HashSet<>();
        allViolations.addAll(parameterViolations);
        allViolations.addAll(beanViolations);

        if (!allViolations.isEmpty()) {
            ConstraintViolation<Object> violation = allViolations.iterator().next();
            log.info(">>>> 유효성 검사 실패: {}", violation.getMessage());
            throw new ValidationException(violation.getMessage());
        }
    }
}
