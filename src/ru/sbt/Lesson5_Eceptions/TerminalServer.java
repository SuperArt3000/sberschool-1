package ru.sbt.Lesson5_Eceptions;


import java.io.IOException;
import java.util.HashMap;
import java.util.Random;


public class TerminalServer {
    HashMap<String, Account> AccountMap;
    TerminalServer(){
        AccountMap = new HashMap<>();
    }
    boolean addAccount(String name, short PIN){
        AccountMap.put(name, new Account(name, PIN));
        return true;
    }


    boolean putMoney(String name, short key, int money) throws IOException, TOException {
        Random rS = new Random();
        int r = rS.ints().limit(1).sum();
        if(r % 3 != 0) throw new TOException("Проводятся регламентные работы");
            if (money % 100 != 0) throw new IOException("Wrong number!");
            AccountMap.get(name).put(money);
            return true;
    }


    boolean getMoney(String name, short key, int money) throws IOException, NotEnoughGoldException, TOException{
        Random rS = new Random();
        int r = rS.ints().limit(1).sum();
        if(r % 3 != 0) throw new TOException("Проводятся регламентные работы");
        if (money % 100 != 0) throw new IOException("Wrong number!");
            if(!AccountMap.get(name).get(money)) throw new NotEnoughGoldException("Your wallet is empty!");
            return true;
    }
}
