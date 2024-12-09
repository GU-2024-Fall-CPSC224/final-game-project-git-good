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

    public Deck() {
        cards = new ArrayList<>();
        for (String value : values) {
            for (Character suit : suits) {
                String imagePath = generateImagePath(value, suit);
                cards.add(new Card(value, suit, imagePath));
            }
        }
    }

    private String generateImagePath(String value, Character suit) {
        String suitName;
        String valueName;
        switch (suit) {
            case '\u2665':
                suitName = "hearts";
                break;
            case '\u2666':
                suitName = "diamonds";
                break;
            case '\u2663':
                suitName = "clubs";
                break;
            case '\u2660':
                suitName = "spades";
                break;
            default:
                suitName = "unknown";
                break;
        }
        switch (value) {
            case "J":
                valueName = "jack";
                break;
            case "Q":
                valueName = "queen";
                break;
            case "K":
                valueName = "king";
                break;
            case "A":
                valueName = "ace";
                break;
            default:
                valueName = value;
                break;
        }
        return "PNG-cards-1.3/" + valueName + "_of_" + suitName + ".png";
    }


    /*
     * Shuffle method
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /*
    Draw method
    */
    public Card draw() {
        if (cards.isEmpty()) {}
            repopulateAndShuffle();
        return cards.remove(0);
    }

    public void repopulateAndShuffle() {
        cards.clear();
        for (String value : values) {
            for (Character suit : suits) {
                String imagePath = generateImagePath(value, suit); // Generate the image path
                cards.add(new Card(value, suit, imagePath));       // Create and add the card with image
            }
        }
        Collections.shuffle(cards); // Shuffle the deck after repopulating
    }

    /*
     * Get size
     */
    public Integer size() {
        return cards.size();
    }
    
}
