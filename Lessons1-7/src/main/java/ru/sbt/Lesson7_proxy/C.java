package ru.sbt.Lesson7_proxy;


public interface C extends B{

    @Cache(Cache.CachType.MEMORY_AND_FILE)
    int product(int a, int b);
}
