package edu.gonzaga;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

// Blackjack Game Class
public class BlackjackGame extends JPanel {
    private final JTextArea playerArea;
    private final JTextArea dealerArea;
    private final JButton hitButton;
    private final JButton standButton;
    private final JLabel statusLabel;
    private final Deck deck;
    private final List<Card> playerHand;
    private final List<Card> dealerHand;
    

    public BlackjackGame(GUIManager manager) {
        setLayout(new BorderLayout());

        // Game areas
        playerArea = new JTextArea();
        dealerArea = new JTextArea();
        statusLabel = new JLabel("Welcome to Blackjack!", SwingConstants.CENTER);

        JPanel gamePanel = new JPanel(new GridLayout(1, 2));
        gamePanel.add(new JScrollPane(playerArea));
        gamePanel.add(new JScrollPane(dealerArea));

        add(gamePanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.NORTH);

        // Buttons
        this.hitButton = new JButton("Hit");
        this.standButton = new JButton("Stand");
        JButton restartButton = new JButton("Restart");
        JButton endButton = new JButton("Quit Game");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        buttonPanel.add(restartButton);
        buttonPanel.add(endButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Game setup
        deck = new Deck();
        playerHand = new ArrayList<>();
        dealerHand = new ArrayList<>();
        startGame();

        // Button actions
        hitButton.addActionListener(e -> hit());
        standButton.addActionListener(e -> stand());
        restartButton.addActionListener(e -> startGame());
        endButton.addActionListener(e -> manager.showScreen("StartScreen"));
    }

    private void startGame() {
        playerHand.clear();
        dealerHand.clear();
        deck.shuffle();
        playerHand.add(deck.draw());
        playerHand.add(deck.draw());
        dealerHand.add(deck.draw());
        dealerHand.add(deck.draw());
        hitButton.setEnabled(true);
        standButton.setEnabled(true);
        updateAreas();
        statusLabel.setText("Your move!");
    }

    private void hit() {
        playerHand.add(deck.draw());
        if (calculateHandValue(playerHand) > 21) {
            statusLabel.setText("You busted! Dealer wins.");
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
        }
        blackjackCheck();
        updateAreas();
    }

    private void stand() {
        while (calculateHandValue(dealerHand) < 12 || calculateHandValue(playerHand) > calculateHandValue(dealerHand)) {
            dealerHand.add(deck.draw());
        }
        determineWinner();
        updateAreas();
    }

    private void blackjackCheck() {
        if (calculateHandValue(playerHand) == 21) {
            statusLabel.setText("Blackjack! You win!");
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
        }
    }

    private void determineWinner() {
        int playerValue = calculateHandValue(playerHand);
        int dealerValue = calculateHandValue(dealerHand);
    
        if (playerValue > 21) {
            statusLabel.setText("You busted! Dealer wins.");
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
        } else if (dealerValue > 21) {
            statusLabel.setText("Dealer busted! You win!");
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
        } else if (playerValue > dealerValue) {
            statusLabel.setText("You win!");
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
        } else if (playerValue < dealerValue) {
            statusLabel.setText("Dealer wins!");
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
        } else {
            statusLabel.setText("Push!");
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
        }
    }

    private int calculateHandValue(List<Card> hand) {
        int totalValue = 0;
        int aceCount = 0;

        for (Card card : hand) {
            totalValue += card.getValue();
            if (card.toString().contains("Ace")) {
                aceCount++;
            }
        }

        while (totalValue > 21 && aceCount > 0) {
            totalValue -= 10;
            aceCount--;
        }

        return totalValue;
    }

    private void updateAreas() {
        playerArea.setText("Your Hand:\n" + handToString(playerHand) + "\nValue: " + calculateHandValue(playerHand));
        dealerArea.setText("Dealer's Hand:\n" + handToString(dealerHand) + "\nValue: " + calculateHandValue(dealerHand));
    }

    private String handToString(List<Card> hand) {
        StringBuilder sb = new StringBuilder();
        for (Card card : hand) {
            sb.append(card).append("\n");
        }
        return sb.toString();
    }


}