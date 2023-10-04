package com.petdiary.core.components;

import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MethodPerformanceAspect {
    @Around("execution(* com.petdiary..*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Timer.Sample sample = Timer.start(Metrics.globalRegistry);
        Object proceed = joinPoint.proceed();
        Timer timer = Metrics.timer("method.execution.time", "class", joinPoint.getSignature().getDeclaringTypeName(), "method", joinPoint.getSignature().getName());
        sample.stop(timer);

        return proceed;
    }
}
