package ru.sbt.Lesson14_JMM;

import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * Created by Yrwing on 26.10.2016.
 */
public class Task<T> {
    private final Callable<? extends T> calledTask;
    volatile T result;
    private volatile boolean exceptionFlag = false;
     public Task(Callable<? extends T> callable){
        this.calledTask = callable;
    }

    public T get() throws RuntimeException{
        if(Objects.isNull(result) && !exceptionFlag) {
            synchronized (this){
            try {
                result = calledTask.call();
            } catch (Exception e) {
                exceptionFlag = true;
            }
        }
        }
        if(exceptionFlag) throw new MyRuntimeException("Всё пропало!!!");
        return result;
    }
}
class MyRuntimeException extends RuntimeException{
    public MyRuntimeException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}