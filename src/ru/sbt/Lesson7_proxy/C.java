package ru.sbt.Lesson7_proxy;


public interface C extends B{

    @Cashe
    int product(int a, int b);
}
