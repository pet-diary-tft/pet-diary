package com.petdiary.domain.redispetdiary.components;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class RedisAspect {
    @Pointcut("execution(* org.springframework.data.redis.core.RedisTemplate.*(..))")
    public void redisMethods() {}

    @AfterReturning(pointcut = "redisMethods()", returning = "result")
    public void afterRedisMethodCall(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        StringBuilder argsStr = new StringBuilder();
        for (Object arg : args) {
            argsStr.append(arg).append(",");
        }

        log.debug("[Redis Call Info] Method: {} - Arguments: [{}] - Result: {}", methodName, argsStr, result);
    }

    @Pointcut("execution(* org.springframework.data.redis.core.ValueOperations.set(..))")
    public void redisSetMethods() {}

    @AfterReturning(pointcut = "redisSetMethods()", returning = "result")
    public void afterRedisSetMethodCall(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        StringBuilder argsStr = new StringBuilder();
        for (Object arg : args) {
            argsStr.append(arg).append(",");
        }

        log.debug("[Redis Call Info] Method: {} - Arguments: [{}] - Result: {}", methodName, argsStr, result);
    }
}
