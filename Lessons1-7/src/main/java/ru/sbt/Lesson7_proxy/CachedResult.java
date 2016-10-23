package ru.sbt.Lesson7_proxy;


import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

class CachedResult implements Serializable{
    private Map<Object[], Object> resultsMap;

    public CachedResult() {
        resultsMap = new HashMap<Object[], Object>();
    }

    void putIfAbsent(Method method, Object delegate, Object[] args) throws Throwable {

        resultsMap.putIfAbsent(args, method.invoke(delegate, args));
    }
    Object get(Object[] args){
        return resultsMap.get(args);
    }
}