package ru.sbt.Lesson5_Eceptions;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by ���� on 24.09.2016.
 */
public class Terminal {
    TerminalServer server;
    PinValidator PinV = new PinValidator(server);
    private Scanner s = new Scanner(System.in);
 /*   public int checkAccount(Scanner s){
        System.out.println("Login:");
        String name = s.nextLine();
        System.out.println("PIN");
        try {
            short key = s.nextShort();
            if(!server.pinCheck(name, key)) throw new IncorrectPINException();
            else
                server.
        }
        catch (IOException | IncorrectPINException e){
            System.out.println("Incorrect PIN");
        }
    }
*/
    public boolean getMoney(){
            System.out.println("Login:");
            String name = s.nextLine();
            short key = s.nextShort();
            if(PinV.pinCheck(name, key)) {
                System.out.println("How much?");
                int money = s.nextInt();
                return server.getMoney(name, key, money);
            }
            else
                return false;
    }

    public boolean putMoney(){
        System.out.println("Login:");
        String name = s.nextLine();
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




  //  PinValidator pinValidator;

