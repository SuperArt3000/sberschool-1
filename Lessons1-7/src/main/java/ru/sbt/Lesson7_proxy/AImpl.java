package ru.sbt.Lesson7_proxy;


import java.io.Serializable;

public class AImpl implements A{
    @Override
    public int out(int i) {
        return(i);
    }
}
