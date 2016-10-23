package ru.sbt.Lesson7_proxy;


public class ABImpl implements A,B {
    @Override
    public int out(int i) {
        return i*10;
    }

    @Override
    public int cashedsum(int a, int b) {
        return 10*(a+b);
    }
}
