package ru.sbt;

public interface ThreadPool {

    void start();

    void execute(Runnable runnable);

    void stop();

}
