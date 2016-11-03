package ru.sbt.Lesson14_JMM;

/**
 * Created by Yrwing on 26.10.2016.
 */
public interface ExecutionManager {

    Context execute(Runnable callback, Runnable[] tasks);
}
