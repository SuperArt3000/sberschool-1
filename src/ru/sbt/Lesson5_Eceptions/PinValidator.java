package ru.sbt.Lesson5_Eceptions;

/**
 * Created by ���� on 25.09.2016.
 */
public class PinValidator {
    private short counter;
    private TerminalServer server;
    public PinValidator(TerminalServer server){
        this.server = server;
    }

     boolean pinCheck(String name, short key){
         try{
             if (key != this.server.AccountMap.get(name).PIN){
             counter++;
             throw new IncorrectPINException();
            }
             else return true;
         }
         catch(IncorrectPINException e){
             if(counter < 3)
                System.out.println("Incorrect PIN");
             else try {
                 System.out.println("Incorrect PIN. Access blocked for 5 seconds.");
                 counter = 0;
                 Thread.sleep(5000);
             } catch (InterruptedException exc) {
                 exc.printStackTrace();
             }
             return false;
         }
    }
    private class IncorrectPINException extends Throwable{}

}

