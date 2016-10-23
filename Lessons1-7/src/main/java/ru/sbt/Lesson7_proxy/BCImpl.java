package ru.sbt.Lesson7_proxy;


public class BCImpl implements C {
    @Override
    public int product(int a, int b) {
        return a*b;
    }

    @Override
    public int cashedsum(int a, int b) {
        return a+b;
    }
}
