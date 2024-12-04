package edu.gonzaga;

public class Player {
    protected Integer balance;
    protected String name;
    
    /*
     * Constructor
     */
    public Player(String name, Integer balance) {
        this.name = name;
        this.balance = 1000;
    }

    public int getBalance() {
        return balance != null ? balance : 0;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void adjustBalance(int amount) {
        if (balance != null) {
            this.balance += amount;
        } else {
            this.balance = amount;
        }
    }

    /*
     * Constructor
     */
    public Player(){
        this.balance = 1000;
    }
}
