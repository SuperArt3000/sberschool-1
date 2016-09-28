package ru.sbt.Lesson5_Eceptions;


import java.io.IOException;
import java.util.HashMap;


public class TerminalServer {
    HashMap<String, Account> AccountMap;
    TerminalServer(){
        AccountMap = new HashMap<>();
    }
    boolean addAccount(String name, short PIN){
        AccountMap.put(name, new Account(name, PIN));
        return true;
    }


    boolean putMoney(String name, short key, int money) {
        try {
            if (money % 100 != 0) throw new IOException();
            AccountMap.get(name).put(money);
            return true;
        }
        catch(IOException e){
            System.out.println("Wrong number!");
            return false;
        }
    }


    boolean getMoney(String name, short key, int money){
        try{
            if (money % 100 != 0) throw new IOException();
            if(!AccountMap.get(name).get(money)) throw new NotEnoughGoldException("Your wallet is empty!");
            return true;
        }
        catch(NotEnoughGoldException e) {
            Exceptions_Messages.showMessage(e.getMessage());
            return false;
        }
        catch(IOException t){
            System.out.println("Wrong number!");
            return false;
        }
    }


}
