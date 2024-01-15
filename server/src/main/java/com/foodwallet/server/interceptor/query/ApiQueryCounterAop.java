package com.foodwallet.server.interceptor.query;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.stereotype.Component;

/**
 * AOP를 이용하여 Connection의 프록시 반환
 *
 * @author 임우택
 */
@Component
@Aspect
public class ApiQueryCounterAop {
    private final ApiQueryCounter apiQueryCounter;

    public ApiQueryCounterAop(final ApiQueryCounter apiQueryCounter) {
        this.apiQueryCounter = apiQueryCounter;
    }

    @Around("execution(* javax.sql.DataSource.getConnection())")
    public Object getConnection(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object connection = proceedingJoinPoint.proceed();
        return Proxy.newProxyInstance(
            connection.getClass().getClassLoader(),
            connection.getClass().getInterfaces(),
            new ConnectionProxyHandler(connection, apiQueryCounter)
        );
    }
}
