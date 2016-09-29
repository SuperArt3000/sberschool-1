package ru.sbt.Lesson5_Eceptions;

import java.io.IOException;
import java.util.Scanner;


public class Terminal {
    private final TerminalServer server;
    private final PinValidator PinV;

    Terminal(TerminalServer t){
        this.server = t;
        PinV = new PinValidator(this.server);
    }

    public void enterAccount(Scanner s) throws Exception{
        System.out.println("Login:");
        String name = s.next();
        if(this.server.AccountMap.containsKey(name)) {
            System.out.println("PIN:");
            short key = s.nextShort();
            if (PinV.pinCheck(name, key)) {

                    switch (s.next()) {
                        case "-get": {
                            this.getMoney(name, key, s);
                            break;
                        }
                        case "-put": {
                            this.putMoney(name, key, s);
                            break;
                        }
                        case "-check": {
                            Exceptions_Messages.showMessage(server.AccountMap.get(name).CheckWallet() + " руб.");
                            break;
                        }
                        default: {
                            System.out.println("Unknown command");
                            break;
                        }
                    }
            }
        }
        else if(name.startsWith("-")) throw new IOException("Login first!");
        else throw new AccountNotExist("Account does not exist.");
    }

    private boolean getMoney(String name, short key, Scanner s){
        System.out.println("How much?");
        int money = s.nextInt();
        return server.getMoney(name, key, money);
    }

    private boolean putMoney(String name, short key, Scanner s){
        System.out.println("How much?");
        int money = s.nextInt();
        return server.putMoney(name, key, money);
    }
}

