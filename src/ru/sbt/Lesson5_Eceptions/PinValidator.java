package ru.sbt.Lesson5_Eceptions;

import javax.security.auth.login.AccountLockedException;
import java.util.Scanner;

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
             throw new IncorrectPINException("Incorrect PIN.");
            }
             else return true;
         }
         catch(IncorrectPINException e) {
             if (counter < 3)
                 System.out.println("Incorrect PIN");
             else {
                 System.out.println("Incorrect PIN. Access blocked for 5 seconds.");
                 counter = 0;
                 try {AccessBlock();}
                 catch(AccountLockedException ALEx){
                     System.out.println(ALEx.getMessage());
                 }
             }
             return false;
         }
    }
    private void AccessBlock() throws AccountLockedException{
        Scanner s = new Scanner(System.in);
        try {
            for (int i = 10; i > 0; i--) {
                Thread.sleep(500);
                if (s.hasNext()) throw new AccountLockedException((0.5*i) + " seconds remain.");
            }
        } catch (InterruptedException exc) {
            exc.printStackTrace();
        }

    }
    public class IncorrectPINException extends Throwable{
        public IncorrectPINException(String msg){super(msg);}
    }
}

