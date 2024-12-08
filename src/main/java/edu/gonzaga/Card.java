package edu.gonzaga;

public class Card {
    private final String value;
    private final Character suit;
    private final String imagePath;

    public Card(String value, Character suit, String imagePath) {
        this.value = value;
        this.suit = suit;
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public String toString() {
        return value + suit + " (" + imagePath + ")";
    }

    public Integer getValue() {
        switch (value) {
            case "A":
                return 11;
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