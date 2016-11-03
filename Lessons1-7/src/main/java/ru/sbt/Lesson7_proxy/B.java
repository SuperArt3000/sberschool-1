package ru.sbt.Lesson7_proxy;


public interface B {
    @Cache(CachType.FILE)
    int cashedsum(int a, int b);
}
