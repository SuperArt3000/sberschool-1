package ru.sbt.Lesson7_proxy;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;


class CachProxy {
    /**
     * В CashedMethods хранятся методы и кэшированные результаты, разбросанные по соответствующим им интерфейсам
     *
     */
    private final CachedClasses cachedClasses;
    private final Class<?>[] interfaces;
    private final Package basePackage;


    CachProxy(Class<?> baseCls) throws Exception {
        this.basePackage = baseCls.getPackage();
        final File baseFile = new File(Thread.currentThread()
                .getContextClassLoader()
                .getResource(basePackage.getName().replace(".", "/"))
                .getFile());
        this.interfaces = InterfaceStreamer.getAllClassesByPredicate(basePackage, baseFile);
        cachedClasses = new CachedClasses(interfaces);

    }

    Object newCashProxy (Object cls){
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
            if(method.isAnnotationPresent(Cache.class)){
                switch(method.getAnnotation(Cache.class).value()) {
                    case FILE:
                        if(isSerialiseble(method)){
                            try {
                                cachedClasses.put(method.getDeclaringClass(),
                                        (ClassMethods) deserialize("C:\\Users\\Yrwing\\IdeaProjects\\sberschool\\Lessons1-7\\src\\main\\Proxy_Cach\\"
                                                + method.getDeclaringClass().getName() + method.getName()));

                            }catch(FileNotFoundException fnfe){
                                System.out.println("Creating new cache file...");
                            }
                        }
                        else{System.out.println("Caching prohibited, invoking method...");}
                        break;
                    case MEMORY: {
                        putIfAbsent(method, delegate, args);
                        break;
                    }
                    case MEMORY_AND_FILE:
                        if(isSerialiseble(method)){
                            try{
                            cachedClasses.put(method.getDeclaringClass(),
                                    (ClassMethods) deserialize("C:\\Users\\Yrwing\\IdeaProjects\\sberschool\\Lessons1-7\\src\\main\\Proxy_Cach\\"
                                            + method.getDeclaringClass().getName() + method.getName()));
                            }catch(FileNotFoundException e) {
                                System.out.println("Creating new cache file...");
                            }
                        }
                        else  putIfAbsent(method, delegate, args);
                        break;

                }
                if (isSerialiseble(method) && (method.getAnnotation(Cache.class).value() != Cache.CachType.MEMORY)){
                    putIfAbsent(method, delegate, args);
                    serialize("C:\\Users\\Yrwing\\IdeaProjects\\sberschool\\Lessons1-7\\src\\main\\Proxy_Cach\\"
                            + method.getDeclaringClass().getName() + method.getName(), cachedClasses.get(method));
                }try {
                    return cachedClasses.get(method).getResult(method, args);
                }catch (NullPointerException npe){
                    return method.invoke(delegate, args);
                }
            }
            else throw new Exception();
        }
    }
    //TODO: разумно ли передавать метод в качестве аргумента, или лучше использовать его имя?
    private void putIfAbsent(Method method, Object delegate, Object[] args) throws Throwable{
        cachedClasses.get(method).putIfAbsent(method, new CachedResult());
        cachedClasses.get(method).putIfAbsent(method, delegate, args);
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

    private Object deserialize(String filename) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(filename);
        ObjectInputStream in = new ObjectInputStream(fis);
        return  in.readObject();
    }

    private boolean isSerialiseble(Method method){
        return Arrays.asList(method.getDeclaringClass().getInterfaces()).contains(Serializable.class);
    }

}


