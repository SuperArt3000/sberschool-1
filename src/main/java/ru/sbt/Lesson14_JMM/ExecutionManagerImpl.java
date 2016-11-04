package ru.sbt.Lesson14_JMM;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * Created by Yrwing on 26.10.2016.
 */
public class ExecutionManagerImpl implements ExecutionManager {

    private volatile ExecutorService cachedPool;
    private volatile List<Future<?>> futureList;

    @Override
    public Context execute(Runnable callback, Runnable[] tasks){
        futureList = new ArrayList<>();
        cachedPool = Executors.newFixedThreadPool(3);
        for (Runnable task:tasks) {
            futureList.add(cachedPool.submit(task));
        }
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cachedPool.awaitTermination(20000, TimeUnit.MILLISECONDS);
                }catch (InterruptedException ie){
                    ie.printStackTrace();
                    cachedPool.shutdown();
                }
                callback.run();
            }
        });
        t.start();

        return new Context(){
            private void filter(){
                futureList.stream().filter(Future::isDone).filter(Objects::isNull).count();
            }

            @Override
            public int getCompletedTaskCount()  {
                    return (int) futureList.stream()
                            .filter(Future::isDone)
                            .filter(f -> {try{return Objects.isNull(f.get());}catch (Exception e){e.printStackTrace(); return false;}})
                            .count();
            }

            @Override
            public int getInterruptedTaskCount() {
                return (int)futureList.stream().filter(Future::isDone).filter(Future::isCancelled).count();
            }

            @Override
            public int getFailedTaskCount() {
                return (int)futureList.stream()
                        .filter(Future::isDone)
                        .filter(f -> (!f.isCancelled()))
                        .filter(f -> {try{
                                    f.get();
                                    return false;
                                }catch(ExecutionException exex){return true;}
                                catch(InterruptedException intex){ return false;}
                                }
                        ).count();
            }

            @Override
            public void interrupt() {
                synchronized(cachedPool) {

                    futureList.stream().forEach(f->f.cancel(false));
                    cachedPool.shutdown();
                }
            }

            @Override
            public boolean isFinished() {
                return cachedPool.isTerminated();
            }
        };
    }
}
