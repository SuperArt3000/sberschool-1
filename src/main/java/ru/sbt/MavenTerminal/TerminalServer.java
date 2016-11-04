package ru.sbt.MavenTerminal;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class TerminalServer {
    Map<String, Account> AccountMap;

    TerminalServer(){
        AccountMap = new HashMap<>();
    }

    boolean addAccount(String name, short PIN){
        AccountMap.put(name, new Account(name, PIN));
        return true;
    }


    boolean putMoney(String name, int money) throws IOException, TOException {
        Random rS = new Random();
        int r = rS.ints().limit(1).sum();
        if(r % 3 != 0) throw new TOException("Проводятся регламентные работы");//Не придумал реального повода бросать это исключение
            if (money % 100 != 0) throw new IOException("Wrong number!");
            AccountMap.get(name).put(money);
            return true;
    }


    boolean getMoney(String name, int money) throws IOException, NotEnoughGoldException{
        if (money % 100 != 0) throw new IOException("Wrong number!");
            if(!AccountMap.get(name).get(money)) throw new NotEnoughGoldException("Your wallet is empty!");
            return true;
    }
}
