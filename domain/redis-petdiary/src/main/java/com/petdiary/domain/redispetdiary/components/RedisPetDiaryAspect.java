package com.petdiary.domain.redispetdiary.components;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * PetDiary Redis Repository 정보를 추적하는 Component
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
@Profile("!prod")
public class RedisPetDiaryAspect {
    @Around("execution(* com.petdiary.domain.redispetdiary.repository..*(..))")
    public Object logPetDiaryRedisRepositoryMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String arguments = Arrays.stream(joinPoint.getArgs())
                .map(arg -> Optional.ofNullable(arg).map(Object::toString).orElse("null"))
                .collect(Collectors.joining(", "));
        log.debug("[Redis Repository Call] Entered {} with argument[s] = {}", joinPoint.getSignature().toShortString(), arguments);
        try {
            Object result = joinPoint.proceed();
            log.debug("[Redis Repository Call] Exited {} with result = {}", joinPoint.getSignature().toShortString(), result.toString());
            return result;
        } catch (IllegalArgumentException e) {
            log.debug("[Redis Repository Call] Illegal argument: {} in {}", arguments, joinPoint.getSignature().toShortString());
            throw e;
        }
    }
}
