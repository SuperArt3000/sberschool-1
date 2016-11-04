package ru.sbt.Lesson7_proxy;


import java.io.Serializable;

public interface A extends Serializable {
    @Cache(CachType.FILE)
    int out(int i);
}
