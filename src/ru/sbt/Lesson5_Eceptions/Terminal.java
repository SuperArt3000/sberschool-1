package ru.sbt.Lesson5_Eceptions;

import java.util.Scanner;


public class Terminal {
    private TerminalServer server;
    private PinValidator PinV;

    Terminal(TerminalServer t){
        this.server = t;
        PinV = new PinValidator(this.server);
    }




    public void checkAccount(Scanner s){
        System.out.println("Login:");
        String name = s.next();
        System.out.println("PIN:");
        short key = s.nextShort();
        if(PinV.pinCheck(name, key)){
            System.out.println(server.AccountMap.get(name).CheckWallet() + " руб.");
        }
    }

    public boolean getMoney(Scanner s){
            System.out.println("Login:");
            String name = s.next();
        System.out.println("PIN:");
        short key = s.nextShort();
            if(PinV.pinCheck(name, key)) {
                System.out.println("How much?");
                int money = s.nextInt();
                return server.getMoney(name, key, money);
            }
            else
                return false;
    }

    public boolean putMoney(Scanner s){
        System.out.println("Login:");
        String name = s.next();
        System.out.println("PIN:");
        short key = s.nextShort();
        if(PinV.pinCheck(name, key)) {
            System.out.println("How much?");
            int money = s.nextInt();
            return server.putMoney(name, key, money);
        }
        else
            return false;
    }
}

