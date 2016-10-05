package ru.sbt.Lesson7_proxy;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

/**
 * Created by Yrwing on 04.10.2016.
 */
public class CashProxy {
    /**
     * В CashedMethods хранятся методы и кэшированные результаты, разбросанные по соответствующим им интерфейсам
     */
    private final Map<Class<?>, HashMap<Method, Object>> CashedMethods;
    private final Package basePackage;
    private final Class<?>[] Interfaces;


    public CashProxy(Class<?> baseCls) throws Exception {
        this.CashedMethods = new HashMap<>();
        this.basePackage = baseCls.getPackage();
        final File baseFile = new File(Thread.currentThread()
                .getContextClassLoader()
                .getResource(basePackage.getName().replace(".", "/"))
                .getFile());
        this.Interfaces = InterfaceStreamer.getAllClassesByPredicate(basePackage, baseFile);
        for (Class<?> item : Interfaces) {
            this.CashedMethods.put(item, new HashMap<>());
        }

    }
    Object newCashProxy (Object cls){
        return Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(),
                Interfaces, new LogHandler(cls));

    }

    private class LogHandler implements InvocationHandler {

        private final Object delegate;

        public LogHandler(Object delegate) {
            this.delegate = delegate;
        }

        final Map<Method, Object> MethodsCashe = new HashMap<>();
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if(method.isAnnotationPresent(Cashe.class)){
                CashedMethods.get(method.getDeclaringClass()).putIfAbsent(method, method.invoke(delegate, args));
                return CashedMethods.get(method.getDeclaringClass()).get(method);
            }
            else throw new Exception();
        }
    }
}
