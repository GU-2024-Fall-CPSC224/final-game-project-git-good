package edu.gonzaga;

/*
 * An object that has a number and a suit.
 * 
 * 
 */

public class Card {

    private String value;
    private Character suit;
    

    /*
     * Constructor
     * 
     * 
     */
    public Card(String value, Character suit){
        this.value = value;
        this.suit = suit;
    }
    
    /*
     * toString if needed
     * 
     */
    public String toString() {
        return value + suit;
    }


}



/*
/* Python example:
HEARTS = chr(9829)
DIAMONDS = chr(9830)
SPADES = chr(9824)
CLUBS = chr(9827)

values = ['A','2','3','4','5','6','7','8','9','10','J','Q','K']
suits = [HEARTS, DIAMONDS, SPADES, CLUBS]

deck = []
for value in values:
    for suit in suits:
        deck.append((value, suit))
 */