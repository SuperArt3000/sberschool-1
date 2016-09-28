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
                 Exceptions_Messages.showMessage("Incorrect PIN");
             else {
                 Exceptions_Messages.showMessage("Incorrect PIN. Access blocked for 5 seconds.");
                 counter = 0;
                 try {Exceptions_Messages.AccessBlock();}
                 catch(AccountLockedException ALEx){
                     Exceptions_Messages.showMessage(ALEx.getMessage());
                 }
             }
             return false;
         }
    }


}

