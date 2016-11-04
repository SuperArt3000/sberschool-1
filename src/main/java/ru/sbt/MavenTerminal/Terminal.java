package ru.sbt.MavenTerminal;

import java.io.IOException;
import java.util.Scanner;

public class Terminal {
    private final TerminalServer server;
    final PinValidator PinV;

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
                            this.getMoney(name, s);
                            break;
                        }
                        case "-put": {
                            this.putMoney(name, s);
                            break;
                        }
                        case "-check": {
                            ExceptionsMessenger.showMessage(server.AccountMap.get(name).CheckWallet() + " руб.");
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

    private boolean getMoney(String name, Scanner s) throws IOException,NotEnoughGoldException, TOException{
        System.out.println("How much?");
        int money = s.nextInt();
        return server.getMoney(name, money);
    }

    private boolean putMoney(String name, Scanner s) throws IOException, TOException{
        System.out.println("How much?");
        int money = s.nextInt();
        return server.putMoney(name, money);
    }
}

