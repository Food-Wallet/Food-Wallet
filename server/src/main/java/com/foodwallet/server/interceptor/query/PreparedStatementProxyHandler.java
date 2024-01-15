package com.foodwallet.server.interceptor.query;

import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 쿼리 실행 시점에 카운트를 증가시키는 다이나믹 프록시
 *
 * @author 임우택
 */
public class PreparedStatementProxyHandler implements InvocationHandler {

    private final Object preparedStatement;
    private final ApiQueryCounter apiQueryCounter;

    public PreparedStatementProxyHandler(Object preparedStatement, ApiQueryCounter apiQueryCounter) {
        this.preparedStatement = preparedStatement;
        this.apiQueryCounter = apiQueryCounter;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (isExecuteQuery(method) && isInRequestScope()) {
            apiQueryCounter.increaseCount();
        }
        return method.invoke(preparedStatement, args);
    }

    private boolean isExecuteQuery(Method method) {
        String methodName = method.getName();
        return methodName.equals("executeQuery") || methodName.equals("execute") || methodName.equals("executeUpdate");
    }

    private boolean isInRequestScope() {
        return Objects.nonNull(RequestContextHolder.getRequestAttributes());
    }
}
