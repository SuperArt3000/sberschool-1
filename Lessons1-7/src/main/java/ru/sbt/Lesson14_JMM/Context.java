package ru.sbt.Lesson14_JMM;

/**
 * Created by Yrwing on 26.10.2016.
 */
public interface Context {
    int getCompletedTaskCount();
    int getFailedTaskCount();
    int getInterruptedTaskCount();
    void interrupt();
    boolean isFinished();
}
