package ru.sbt.Lesson7_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


public class Test {
    public static void main(String[] args) {
        AImpl AImpl_test = new AImpl();
        A_B_Impl ABtest = new A_B_Impl();
        B_C_Impl BCtest = new B_C_Impl();
        try{
            CashProxy CProxyImpl = new CashProxy(Test.class);

            A A_proxy = (A) CProxyImpl.newCashProxy(AImpl_test);
            C C_proxy = (C) CProxyImpl.newCashProxy(BCtest);
            A A_proxy2 = (A) CProxyImpl.newCashProxy(ABtest);

            System.out.println(A_proxy.out(3));
            System.out.println(A_proxy.out(5));
            System.out.println(A_proxy2.out(5));

            System.out.println(C_proxy.cashedsum(5, 6));
            System.out.println(C_proxy.cashedsum(5, 6));


            B B_proxy = (B) CProxyImpl.newCashProxy(ABtest);
            System.out.println(B_proxy.cashedsum(5, 6));

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
