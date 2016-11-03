package ru.sbt.Lesson14_JMM;


public class Main {
    public static void main(String[] args) {
       /* Task<Integer> task = new Task<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                //Thread.sleep(1000);
                return (IntStream.iterate(1, t -> t++).limit(8).reduce(1, (x, y) -> x + y));
            }
        });
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable(){
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName() + "__" + task.get() + ";");
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }).start();
        }*/
        Goal[] goals = new Goal[10];
        for (int i = 0; i < 10; i++) {
            goals[i] = new Goal(i);
        }
        ExecutionManagerImpl exManager = new ExecutionManagerImpl();
        Context context = exManager.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("Callback achieved!");
            }
        }, goals);
        System.out.println(context.getCompletedTaskCount());
        context.interrupt();
        System.out.println(context.getInterruptedTaskCount());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(context.isFinished());
    }

}
class Goal implements Runnable{
    private int time;
    public Goal(int i){
        time = i;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(time*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "__Goal achieved.");
    }
}