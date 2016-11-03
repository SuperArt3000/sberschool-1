package ru.sbt.Lesson7_proxy;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;




class ClassMethods implements Serializable {
    private Map<String, CachedResult> cachedMethods;


    ClassMethods() {
        cachedMethods = new HashMap<>();
    }

    Object getResult(String key, Object[] args){
        return cachedMethods.get(key).get(args);
    }

    CachedResult results(String key){
        return cachedMethods.get(key);
    }

    void putIfAbsent(Method method, Object delegate, Object[] args) throws Throwable {
        cachedMethods. get(method.getName())
                .putIfAbsent(method, delegate, args);
    }
    void putIfAbsent(Method method, CachedResult value) {
        cachedMethods.putIfAbsent(method.getName(), value);
    }
    boolean find(String methodName, Object[] args){
        return (cachedMethods.containsKey(methodName) && cachedMethods.get(methodName).find(args));
    }
}
