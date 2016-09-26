package ru.sbt.Lesson5_Eceptions;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by ���� on 24.09.2016.
 */
public class TerminalServer {
    HashMap<String, Account> AccountMap= new HashMap<>();

    boolean addAccount(String name, short PIN){
        AccountMap.put(name, new Account(name, PIN));
        return true;
    }


    boolean putMoney(String name, short key, int money) {
        try {
            if (money % 100 != 0) throw new IOException();
            System.out.println("Check: " + AccountMap.get(name).CheckWallet());
            AccountMap.get(name).put(money);
            System.out.println("Check: " + AccountMap.get(name).CheckWallet());
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
            System.out.println("Check: " + AccountMap.get(name).CheckWallet());
            if(!AccountMap.get(name).get(money)) throw new NotEnoughGoldException();
            System.out.println("Check: " + AccountMap.get(name).CheckWallet());
            return true;
        }
        catch(NotEnoughGoldException e) {
            System.out.println(e.getMessage());
            return false;
        }
        catch(IOException t){
            System.out.println("Wrong number!");
            return false;
        }
    }

    private class NotEnoughGoldException extends Throwable{
    }
}
