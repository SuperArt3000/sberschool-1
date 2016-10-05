package ru.sbt.Lesson7_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yrwing on 04.10.2016.
 */
public class Test {
    public static void main(String[] args) {
        AImpl AImpl_test = new AImpl();
        try{
            CashProxy CProxyImpl = new CashProxy(Test.class);
            A A_proxy = (A) CProxyImpl.newCashProxy(AImpl_test);
            System.out.println(A_proxy.out(3));
            System.out.println(A_proxy.out(5));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class LogHandler implements InvocationHandler {

        private final Object delegate;

        public LogHandler(Object delegate) {
            this.delegate = delegate;
        }

        final Map<Method, Object> MethodsCashe = new HashMap<>();
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if(method.isAnnotationPresent(Cashe.class)){
                MethodsCashe.putIfAbsent(method, method.invoke(delegate, args));
                return MethodsCashe.get(method);
            }
            else throw new Exception();
        }
    }
}
