package ru.sbt.MavenTerminal;


 class Account {
    String login;
    private int wallet;
    final short PIN;

    Account(String name, short key){
        this.login = name;
        this.PIN = key;
    }

    void put(int money){
            this.wallet = +money;
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

