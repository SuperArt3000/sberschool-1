package ru.sbt;

/**
 * Created by Yrwing on 01.11.2016.
 */
public class Main {
    public static void main(String[] args) {
        /*ThreadPool threadPool = new FixedThreadPool(6);
        for (int i = 0; i < 8; i++) {
            threadPool.execute(new Goal(i));
        }
        threadPool.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadPool.stop();
        System.out.println("_______");*/
        ThreadPool threadPool1 = new ScalableThreadPool(2, 8);
        for (int i = 0; i < 20; i++) {
            threadPool1.execute(new Goal(i));
        }
        threadPool1.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadPool1.stop();

    }
}
class Goal implements Runnable {
    private int time;

    public Goal(int i) {
        time = i;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "__Goal_"+time+" achieved.");
    }
}