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

    public Integer getValue() {
        switch (value) {
            case "A":
                return 1;
            case "J":
            case "Q":
            case "K":
                return 10;
            default:
                return Integer.valueOf(value);
        }
    }

    public String getValueStr(){
        return value;
    }


    public String getSuit(){
        return suit.toString();
    }
}