package ru.sbt.Lesson5_Eceptions;


 class Account {
    String login;
    private int wallet;
    final short PIN;

    Account(String name, short key){
        this.login = name;
        this.PIN = key;
    }

    boolean put(int money){
            this.wallet = +money;
            return true;
    }

    boolean get(int money){
        if((money <= this.wallet)){
            this.wallet =- money;
            return true;
        }
        else return false;
    }
    int CheckWallet(){
        return wallet;
    }
}

