package ru.sbt;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Yrwing on 29.10.2016.
 */
public class FixedThreadPool implements ThreadPool {
    private volatile Queue<Runnable> taskQueue = new LinkedBlockingQueue<Runnable>();
    private volatile List<Worker> threadList;
    private  boolean stopFlag = true;
    private int threadListSize;
    FixedThreadPool(int size){
        threadList = new ArrayList<Worker>(size);
        this.threadListSize = size;
    }

    public void start() {
        stopFlag = false;
        for (int i = 0; i < threadListSize; i++) {
            threadList.add(new Worker());
        }
        threadList.forEach(Worker::startWorker);
    }

    public synchronized void stop(){
        threadList.forEach(Worker::stop);
        stopFlag = true;
    }
    public synchronized void execute(Runnable task){
        taskQueue.add(task);
        synchronized(taskQueue) {
            taskQueue.notifyAll();
        }
    }


    private class Worker {
        Thread t;
        Lock taskLock = new ReentrantLock();

        private volatile boolean isFree = true;
        private volatile boolean isStopped = true;
        private volatile Runnable currentTask;

        void newTask(Runnable task) {
            currentTask = task;
        }

        void startWorker() {
            t = new Thread(new Runnable() {

                public void run() {
                    isStopped = false;
                    while (!isStopped) {
                        if (currentTask != null) {
                            taskLock.lock();
                            try {
                                isFree = false;
                                currentTask.run();
                            } finally {
                                taskLock.unlock();
                            }
                        }
                        getNewTask();
                    }
                }
            });
            t.start();
        }

        private void getNewTask() {
            if (taskQueue.isEmpty()) {
                synchronized (taskQueue) {
                    try {
                        taskQueue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            taskLock.lock();
            try {
                this.currentTask = taskQueue.poll();
            } finally {
                taskLock.unlock();
            }
        }

        void stop() {
            isStopped = true;
        }
    }
}


