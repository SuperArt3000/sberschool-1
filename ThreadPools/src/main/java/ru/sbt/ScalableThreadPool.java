package ru.sbt;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ScalableThreadPool implements ThreadPool {
    private final  Queue<Runnable> taskQueue = new LinkedBlockingQueue<Runnable>();
    private final List<Worker> threadList;
    private  volatile boolean stopFlag = true;
    private final int min;
    private final int max;
    private final Object queueMonitor = new Object();

    ScalableThreadPool(int min, int max){
        threadList = new ArrayList<Worker>(min);
        this.min = min;
        this.max = max;
    }

    public void start() {
        Thread mainThread = new Thread(new Runnable() {
            @Override
            public void run() {
                stopFlag = false;
                for (int i = 0; i < min; i++) {
                    threadList.add(new Worker());
                }
                threadList.forEach(Worker::startWorker);//todo constructor worker?

                while (!stopFlag && (threadList.size() < max)) {
                    if (!taskQueue.isEmpty()) {
                        threadList.add(new AdditionalWorker(taskQueue.poll()));
                    }
                }
            }
        });
        mainThread.start();
    }

    public synchronized void stop(){
        threadList.forEach(Worker::stop);
        stopFlag = true;
    }
    public synchronized void execute(Runnable task){
        synchronized(taskQueue) {
            taskQueue.add(task);
            taskQueue.notify();
        }
    }


    private class Worker {
        Thread t;
        Lock taskLock = new ReentrantLock();

        protected volatile boolean isStopped = true;
        protected volatile Runnable currentTask;
        protected volatile boolean isFree = true;
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
        protected void getNewTask() {
            if (taskQueue.isEmpty()) {
                synchronized (taskQueue) {
                    if (taskQueue.isEmpty()) {
                        try {
                            taskQueue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
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
    private class AdditionalWorker extends  Worker{


        AdditionalWorker(Runnable firstTask){
            currentTask = firstTask;
            this.startWorker();
        }
        @Override
        protected void getNewTask() {
            if (taskQueue.isEmpty()) {
                isStopped = true;
            }
            else {
                synchronized(taskQueue){
                    this.currentTask = taskQueue.poll();
                }
            }
            if (currentTask == null) this.isStopped = true;
        }
    }
}
