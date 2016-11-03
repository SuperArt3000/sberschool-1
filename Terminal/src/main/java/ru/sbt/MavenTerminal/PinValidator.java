package ru.sbt.MavenTerminal;

import javax.security.auth.login.AccountLockedException;

/**
 * Created by ���� on 25.09.2016.
 */
public class PinValidator {
    private short mistakeCounter;
    private TerminalServer server;
    public PinValidator(TerminalServer server){
        this.server = server;
    }

     boolean pinCheck(String name, short key){
         try{
             if (key != this.server.AccountMap.get(name).PIN){
             mistakeCounter++;
             throw new IncorrectPINException("Incorrect PIN.");
            }
             else return true;
         }
         catch(IncorrectPINException e) {
             if (mistakeCounter < 3)
                 ExceptionsMessenger.showMessage("Incorrect PIN");
             else {
                 ExceptionsMessenger.showMessage("Incorrect PIN. Access blocked for 5 seconds.");
                 mistakeCounter = 0;
                 try {
                     ExceptionsMessenger.AccessBlock(System.in);}
                 catch(AccountLockedException ALEx){
                     ExceptionsMessenger.showMessage(ALEx.getMessage());
                 }
             }
             return false;
         }
    }


}

