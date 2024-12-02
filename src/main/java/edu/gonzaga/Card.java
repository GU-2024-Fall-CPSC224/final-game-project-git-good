package edu.gonzaga;

class Card {
    private final String suit;
    private final String rank;
    private final int value;

    public Card(String suit, String rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }
    @Override
    public String toString() {
        return rank + " of " + suit;
    }

    public int getValue() {
        return value;
    }
}