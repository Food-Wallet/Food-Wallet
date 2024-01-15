package com.foodwallet.server.interceptor.query;

import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

import java.lang.reflect.Method;

/**
 * Connection의 다이나믹 프록시
 *
 * @author 임우택
 */
public class ConnectionProxyHandler implements InvocationHandler {

    private final Object connection;
    private final ApiQueryCounter apiQueryCounter;

    public ConnectionProxyHandler(final Object connection, final ApiQueryCounter apiQueryCounter) {
        this.connection = connection;
        this.apiQueryCounter = apiQueryCounter;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        Object invokeResult = method.invoke(connection, args); // (1)
        if (method.getName().equals("prepareStatement")) {
            return Proxy.newProxyInstance(
                invokeResult.getClass().getClassLoader(),
                invokeResult.getClass().getInterfaces(),
                new PreparedStatementProxyHandler(invokeResult, apiQueryCounter)
            );
        }
        return invokeResult;
    }
}
