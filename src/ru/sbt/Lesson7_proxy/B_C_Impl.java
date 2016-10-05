package ru.sbt.Lesson7_proxy;


public class B_C_Impl implements C {
    @Override
    public int product(int a, int b) {
        return a*b;
    }

    @Override
    public int cashedsum(int a, int b) {
        return a+b;
    }
}
