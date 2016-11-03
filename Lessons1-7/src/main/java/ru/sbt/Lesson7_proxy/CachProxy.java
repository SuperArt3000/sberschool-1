package ru.sbt.Lesson7_proxy;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static ru.sbt.Lesson7_proxy.CachType.FILE;


class CachProxy {

    private final ReadWriteLock rwlock = new ReentrantReadWriteLock();
    private final Lock rLock = rwlock.readLock();
    private final Lock wLock = rwlock.writeLock();
    /**
     * В CashedMethods хранятся методы и кэшированные результаты, разбросанные по соответствующим им интерфейсам
     *
     */
    private final ClassMethods classesMethods;
    private final Class<?>[] interfaces;
    private final Package basePackage;


    CachProxy(Class<?> baseCls) throws Exception {
        this.basePackage = baseCls.getPackage();
        final File baseFile = new File(Thread.currentThread()
                .getContextClassLoader()
                .getResource(basePackage.getName().replace(".", "/"))
                .getFile());
        this.interfaces = InterfaceStreamer.getAllClassesByPredicate(basePackage, baseFile);
        classesMethods = new ClassMethods();

    }

    synchronized Object  newCashProxy (Object cls){
        return Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(),
                interfaces, new LogHandler(cls));
    }

    private class LogHandler implements InvocationHandler {

        private final Object delegate;

        public LogHandler(Object delegate) {
            this.delegate = delegate;
        }
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if(method.isAnnotationPresent(Cache.class)) {
                CachType type = method.getAnnotation(Cache.class).value();
                CachedResult methodOutputs =null;
                String methodName = getMethodCacheName(method);
                if (isSerialiseble(method)) {
                    rLock.lock();
                    try {
                        methodOutputs = type.readSerializedCache(methodName);
                    }finally{
                        rLock.unlock();
                    }

                } else if (type == FILE) {
                    System.out.println("Caching prohibited, invoking method...");
                }
                if(methodOutputs == null){
                    rLock.lock();
                    try {
                        if (classesMethods.find(methodName, args)) {
                            return classesMethods.getResult(methodName, args);
                        }else {
                            rLock.unlock();
                            wLock.lock();
                            try {
                                putIfAbsent(method, delegate, args);
                            }finally {
                                wLock.unlock();
                                rLock.lock();
                            }
                        }
                    }finally{
                        rLock.unlock();
                    }
                }
                if (isSerialiseble(method) && (method.getAnnotation(Cache.class).value() != CachType.MEMORY)) {
                    wLock.lock();
                    try {
                        serialize("C:\\Users\\Yrwing\\IdeaProjects\\sberschool\\Lessons1-7\\src\\main\\Proxy_Cach\\"
                                + methodName, classesMethods.results(methodName));
                    }finally {
                        wLock.unlock();
                    }
                }
                try {
                    return classesMethods.getResult(methodName, args);
                } catch (NullPointerException npe) {
                    return method.invoke(delegate, args);
                }
            }
            else throw new Exception();
        }
    }
    private void putIfAbsent(Method method, Object delegate, Object[] args) throws Throwable{
        classesMethods.putIfAbsent(method, new CachedResult());
        classesMethods.putIfAbsent(method, delegate, args);
    }

    /**
     * Serialization methods
     * TODO: при изменении кода или запуска на другой машине может выпасть ошибка InvalidClassException.
     * TODO: Я просто удаляю старый кэш-файл. Переписать versionUID?
     */
    private void serialize(String filename, Object serializedClass) throws  IOException,FileNotFoundException{
        FileOutputStream fos = new FileOutputStream(filename);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(serializedClass);
    }

    private boolean isSerialiseble(Method method){
        return Arrays.asList(method.getDeclaringClass().getInterfaces()).contains(Serializable.class);
    }
    private String getMethodCacheName(Method method){
        return method.getDeclaringClass().getName() + "_" + method.getName();
    }
}


