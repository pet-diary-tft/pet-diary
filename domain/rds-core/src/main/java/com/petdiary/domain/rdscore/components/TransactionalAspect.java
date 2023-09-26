package com.petdiary.domain.rdscore.components;

import com.petdiary.domain.rdscore.DomainCoreConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

/**
 * Transactional 정보를 추적하는 Component
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
@Profile("!prod")
public class TransactionalAspect {
    private final ApplicationContext applicationContext;

    @Before("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void beforeTransactional(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String transactionManagerName = determineTransactionManagerName(joinPoint);
        boolean isReadOnly = determineIsReadOnly(joinPoint);
        Object bean = applicationContext.getBean(transactionManagerName);
        if(bean instanceof PlatformTransactionManager) {
            log.debug("[Transactional Info] Class: " + className + " | Method: " + methodName
                    + " | Using TransactionManager: " + transactionManagerName
                    + ", readOnly: " + isReadOnly);
        }
    }

    private boolean determineIsReadOnly(JoinPoint joinPoint) {
        // @Transactional 어노테이션에서 readOnly 속성을 가져옴
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Transactional transactional = method.getAnnotation(Transactional.class);

        if (transactional == null) {
            return false;
        }

        return transactional.readOnly();
    }

    private String determineTransactionManagerName(JoinPoint joinPoint) {
        // @Transactional 어노테이션에서 transactionManager 속성을 가져옴
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Transactional transactional = method.getAnnotation(Transactional.class);

        if (transactional == null) {
            log.debug("[Transactional Info] Transactional is null.");
            return DomainCoreConstants.DEFAULT_TRANSACTION_MANAGER;  // 기본값 반환
        }

        String transactionManagerName = transactional.transactionManager();

        // transactionManager 속성이 빈 문자열인 경우 value 속성을 확인
        if (transactionManagerName.isEmpty()) {
            transactionManagerName = transactional.value();
        }

        // 그래도 transactionManagerName가 빈 문자열인 경우 기본값을 반환
        if (transactionManagerName.isEmpty()) {
            log.debug("[Transactional Info] Transactional name is empty.");
            return DomainCoreConstants.DEFAULT_TRANSACTION_MANAGER;
        }
        else {
            return transactionManagerName;
        }
    }
}
