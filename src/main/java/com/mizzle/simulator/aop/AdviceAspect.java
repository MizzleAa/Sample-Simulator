package com.mizzle.simulator.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class AdviceAspect {
    
    private final Logger log = LoggerFactory.getLogger("LogAspect");

    // @Around("execution(* com.mizzle.simulator.service.DashboardService.*(..))")
    @Around("execution(* com.mizzle.simulator.advice.ApiControllerAdvice.*(..)) || execution(* com.mizzle.simulator.advice.ViewControllerAdvice.*(..))")
    public Object adviceController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable, Exception {
        log.warn("[AdviceInfo] classPath = {}.{}", proceedingJoinPoint.getSignature().getDeclaringTypeName(), proceedingJoinPoint.getSignature().getName());
        Object result = proceedingJoinPoint.proceed();
        return result;
    }
}
