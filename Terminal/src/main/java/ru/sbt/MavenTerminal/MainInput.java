package ru.sbt.MavenTerminal;


import java.util.Scanner;

public class MainInput {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        TerminalServer server = new TerminalServer();
        System.out.println(test1( null));
        short key = 1234;
        server.addAccount("Вася", key);
        key = 1000;
        server.addAccount("Коля", key);
        key = 2000;
        server.addAccount("John", key);
        key = 1001;
        server.addAccount("Jane", key);
        key = 2300;
        server.addAccount("Виктор", key);
        Terminal consoleTerminal = new Terminal(server);
        do {
            try {
                consoleTerminal.enterAccount(s);
            } catch (Exception e) {
                ExceptionsMessenger.showMessage(e.getMessage());
            }
            ExceptionsMessenger.showMessage("Quit y/n?");
        } while(s.next() != "y");
    }

    static String test1(String s){
        return "x1";
    }
}

