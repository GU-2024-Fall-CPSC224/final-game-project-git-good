package edu.gonzaga;

import java.util.ArrayList;
import java.util.Collections;

/*
 * The deck class to store a list of cards
 * 
 * 
 */
public class Deck {
    private final String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private final Character HEARTS = '\u2665';
    private final Character DIAMONDS = '\u2666';
    private final Character CLUBS = '\u2663';
    private final Character SPADES = '\u2660';
    private final Character[] suits = {HEARTS, DIAMONDS, CLUBS, SPADES};

    private final ArrayList<Card> cards;
    
    /*
     * Constructor
     */
    public Deck(){
        cards = new ArrayList<>();

        for (String value : values) {
            for (char suit : suits) {
                cards.add(new Card(value, suit));
            }
        }
    }

    /*
     * Shuffle method
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /*
     * Get size
     */
    public Integer size() {
        return cards.size();
    }
    
}
