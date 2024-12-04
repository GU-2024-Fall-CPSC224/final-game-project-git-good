package edu.gonzaga;

public class Player {
    protected Integer balance;
    protected String name;
    
    /*
     * Constructor
     */
    public Player(String name, Integer balance) {
        this.name = name;
        this.balance = 10000;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    /*
     * Constructor
     */
    public Player(){}
}
