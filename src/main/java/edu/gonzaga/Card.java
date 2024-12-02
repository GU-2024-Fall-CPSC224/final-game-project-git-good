package edu.gonzaga;

class Card {
    private final String value;
    private final Character suit;
    

    public Card(String value, Character suit) {
        this.value = value;
        this.suit = suit;
    }
    @Override
    public String toString() {
        return value + " of " + suit;
    }

    // public Integer getValue() {
    //     return Integer(value);
    // }
}