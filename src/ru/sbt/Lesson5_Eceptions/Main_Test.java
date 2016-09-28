package ru.sbt.Lesson5_Eceptions;


import java.io.IOException;
import java.util.Scanner;

public class Main_Test {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        TerminalServer server = new TerminalServer();
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
                Exceptions_Messages.showMessage(e.getMessage());
            }
            Exceptions_Messages.showMessage("Quit?");
        } while(s.next() != "Yes");
    }
}
