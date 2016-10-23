package ru.sbt.Lesson7_proxy;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yrwing on 23.10.2016.
 */
public class CachedClasses {
    private final Map<Class<?>, ClassMethods> clazzMap;




    public CachedClasses(Class<?>[] interfaces) throws Exception  {
        this.clazzMap = new HashMap<>();
        for (Class<?> item : interfaces) {
            this.clazzMap.put(item, new ClassMethods());
        }
    }

    public void put(Class<?> clazz, ClassMethods clazzMtds){
        clazzMap.put(clazz, clazzMtds);
    }

    public ClassMethods get(Method method){
        return clazzMap.get(method.getDeclaringClass());
    }
}

class ClassMethods implements Serializable {
    private Map<String, CachedResult> cachedMethods;

    ClassMethods() {
        cachedMethods = new HashMap<>();
    }

    Object getResult(Method key, Object[] args){
        return cachedMethods.get(key.getName()).get(args);
    }

    void putIfAbsent(Method method, Object delegate, Object[] args) throws Throwable {
        cachedMethods.get(method.getName())
                .putIfAbsent(method, delegate, args);
    }
    void putIfAbsent(Method method, CachedResult value) {
        cachedMethods.putIfAbsent(method.getName(), value);
    }
}
