package ru.sbt.Lesson7_proxy;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) {
        AImpl AImpl_test = new AImpl();
        ABImpl abTest = new ABImpl();
        BCImpl bcTest = new BCImpl();
        try{
            CachProxy CProxyImpl = new CachProxy(Test.class);
            A a = (A) CProxyImpl.newCashProxy(AImpl_test);
            C c = (C) CProxyImpl.newCashProxy(bcTest);
            A a2 = (A) CProxyImpl.newCashProxy(abTest);
            B B_proxy = (B) CProxyImpl.newCashProxy(abTest);

            ExecutorService executorService = Executors.newFixedThreadPool(6);
           executorService.submit(new Runnable() {
               @Override
               public void run() {
                   try {
                       Thread.sleep(1000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   System.out.println(Thread.currentThread().getName()+"__"+a.out(3));
               }
           });
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"__"+a.out(5));
                }
            });
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"__"+a2.out(5));
                }
            });
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"__"+c.cashedsum(5, 6));
                }
            });
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"__"+c.cashedsum(5, 6));
                }
            });
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"__"+B_proxy.cashedsum(5, 6));
                }
            });
            executorService.shutdown();
            executorService.awaitTermination(5000, TimeUnit.MILLISECONDS);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
