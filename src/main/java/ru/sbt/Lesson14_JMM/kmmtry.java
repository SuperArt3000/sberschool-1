package ru.sbt.Lesson14_JMM;

/**
 * Created by Yrwing on 26.10.2016.
 */
public class kmmtry implements Runnable{
    boolean keepRunning = true;
    public void run(){

        for (int i = 0; i < 4; i++) {

            System.out.print(i+"__");
        }
    }
    public void setKeepRunning(boolean v){keepRunning = v;}
}
