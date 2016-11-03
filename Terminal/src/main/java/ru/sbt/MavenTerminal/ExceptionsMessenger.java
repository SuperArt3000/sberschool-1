package ru.sbt.MavenTerminal;

import javax.security.auth.login.AccountLockedException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by Yrwing on 28.09.2016.
 */
public class ExceptionsMessenger {
    /**
     * Этот метод осуществляет блокировку доступа в течении 5 сек.
     * Бросает исключение при попытке ввода данных.
     * @throws AccountLockedException
     */
    public static void AccessBlock(InputStream in) throws AccountLockedException {
        Scanner s = new Scanner(in);
        try {
            for (int i = 10; i > 0; i--) {
                Thread.sleep(250);
                try {
                    if (s.hasNext()) throw new AccountLockedException((0.5 * i) + " seconds remain.");
                }finally{
                    Thread.sleep(250);
                }
            }
        } catch (InterruptedException exc) {
            exc.printStackTrace();
        }
    }

    /**
     * Метод-заглушка для обработки вывода. По умолчанию - в консоль
     * @param msg
     */
    public static void showMessage(String msg){
        System.out.println(msg);
    }
/**
 * Бросается, если введён неверный PIN.
 * В классе PinValidator.
 */
}
class IncorrectPINException extends Exception{
    public IncorrectPINException(String msg){super(msg);}
}

/**
 * Бросается, если на счёте недостаточно денег.
 * В классе TerminalServer.
 */
class NotEnoughGoldException extends Exception{
    public NotEnoughGoldException(String msg){super(msg);}
}

/**
 * Бросается если ключ Login не найден в AccountMap.
 */
class AccountNotExist extends Exception{
    public AccountNotExist(String msg){super(msg);}

}

class TOException extends Exception{
    public TOException(String msg){super(msg);}

}