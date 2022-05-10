package com.mizzle.simulator.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    private final Logger log = LoggerFactory.getLogger("LogAspect");

    @Around("execution(* com.mizzle.simulator.api.*.*(..))")
    public Object ApiLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable, Exception {
        log.info("[LogInfo] classPath = {}.{}", proceedingJoinPoint.getSignature().getDeclaringTypeName(), proceedingJoinPoint.getSignature().getName());
        Object result = proceedingJoinPoint.proceed();
        return result;
    }

    @Around("execution(* com.mizzle.simulator.controller.*.*(..))")
    public Object ControllerLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable, Exception {
        //log.info("start = {} / {}", proceedingJoinPoint.getSignature().getDeclaringTypeName(), proceedingJoinPoint.getSignature().getName());
        Object result = proceedingJoinPoint.proceed();
        //log.info("end = {} / {}", proceedingJoinPoint.getSignature().getDeclaringTypeName(), proceedingJoinPoint.getSignature().getName());
        return result;
    }
}
