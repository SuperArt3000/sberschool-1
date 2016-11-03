package ru.sbt.Lesson7_proxy;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public enum CachType {
    MEMORY{public CachedResult readSerializedCache( String methodName){return null;//TODO лишний метод
    }}, FILE {
        public CachedResult readSerializedCache(String methodName) throws Exception {
                try {
                    return (CachedResult) deserialize("C:\\Users\\Yrwing\\IdeaProjects\\sberschool\\Lessons1-7\\src\\main\\Proxy_Cach\\" + methodName);
                } catch (FileNotFoundException fnfe) {
                    System.out.println("Creating new cache file...");
                    return null;
                }
            }
        },
        MEMORY_AND_FILE {
            public CachedResult readSerializedCache(String methodName) throws Exception {
                try {
                    return (CachedResult) deserialize("C:\\Users\\Yrwing\\IdeaProjects\\sberschool\\Lessons1-7\\src\\main\\Proxy_Cach\\" + methodName);
                } catch (FileNotFoundException fnfe) {
                    System.out.println("Creating new cache file...");
                    return null;
                }
            }
        };
    abstract public CachedResult readSerializedCache(String methodNamed) throws Exception;

    static private Object deserialize(String filename) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(filename);
        ObjectInputStream in = new ObjectInputStream(fis);
        return  in.readObject();
    }

    }
