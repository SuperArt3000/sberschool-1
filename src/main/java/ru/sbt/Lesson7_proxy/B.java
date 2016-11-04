package ru.sbt.Lesson7_proxy;


public interface B {
    @Cache(CachType.MEMORY)
    int cashedsum(int a, int b);
}
