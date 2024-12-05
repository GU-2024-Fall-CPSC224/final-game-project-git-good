package edu.gonzaga;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

// Blackjack Game Class
public class BlackjackGame extends JPanel {
    private final JTextArea playerArea;
    private final JTextArea dealerArea;
    private final JButton hitButton;
    private final JButton standButton;
    private final JButton betButton = new JButton("Place Bet");
    private final JLabel statusLabel;
    private final Deck deck;
    private final List<Card> playerHand;
    private final List<Card> dealerHand;
    private int bet;
    private final JTextField betField;
    private final Player player;

    private final JButton continueButton = new JButton("Next Round");
    private int roundCount;
    private final List<String[]> roundResults = new ArrayList<>();

    private final JLabel balanceLabel = new JLabel("Balance: $1000", SwingConstants.CENTER);
    private final JPanel buttonPanel;
    private final JPanel betPanel;

    // Predefined bet amounts
    private final int[] predefinedBets = {10, 20, 50, 100, 200};
    private List<Integer> balanceChanges = new ArrayList<>();

    public BlackjackGame(GUIManager manager) {
        setLayout(new BorderLayout());

        player = new Player();

        // Game areas
        playerArea = new JTextArea();
        dealerArea = new JTextArea();
        betField = new JTextField(5);
        statusLabel = new JLabel("Welcome to Blackjack!", SwingConstants.CENTER);

        JPanel gamePanel = new JPanel(new GridLayout(1, 2));
        gamePanel.add(new JScrollPane(playerArea));
        gamePanel.add(new JScrollPane(dealerArea));

        add(gamePanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.NORTH);

        // Initialize button panels
        buttonPanel = new JPanel();
        betPanel = new JPanel();

        this.hitButton = new JButton("Hit");
        this.standButton = new JButton("Stand");
        JButton restartButton = new JButton("Restart");
        JButton endButton = new JButton("Finish Game");

        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        buttonPanel.add(restartButton);
        buttonPanel.add(endButton);
        buttonPanel.add(betButton);
        buttonPanel.add(continueButton);

        hitButton.setEnabled(false);
        standButton.setEnabled(false);
        restartButton.setEnabled(false);
        continueButton.setEnabled(false);

        add(buttonPanel, BorderLayout.SOUTH);
        add(balanceLabel, BorderLayout.NORTH);

        // Game setup
        deck = new Deck();
        playerHand = new ArrayList<>();
        dealerHand = new ArrayList<>();
        roundCount = 1;
        startGame();

        // Button actions
        hitButton.addActionListener(e -> hit());
        standButton.addActionListener(e -> stand());
        betButton.addActionListener(e -> showBetOptions());
        restartButton.addActionListener(e -> startGame());
        endButton.addActionListener(e -> {
            manager.setClosingScreenData(roundResults, balanceChanges);
            manager.showScreen("ClosingScreen");
        });
        continueButton.addActionListener(e -> nextRound());
    }

    private void showBetOptions() {

        remove(buttonPanel);
        
        betPanel.removeAll();
        for (int betAmount : predefinedBets) {
            JButton betOption = new JButton("$" + betAmount);
            betOption.addActionListener(e -> placeBet(betAmount));
            betPanel.add(betOption);
        }
        
        add(betPanel, BorderLayout.SOUTH);
        revalidate();
        repaint();
    }

    private void placeBet(int amount) {
        player.placeBet(amount);
        statusLabel.setText("Bet Placed: $" + amount);
        updateBalanceDisplay();
        resetBetOptions();
        disableBettingPhase();
    }

    private void disableBettingPhase() {
        betPanel.setEnabled(false);
    }

    private void updateBalanceDisplay() {
        balanceLabel.setText("Balance: $" + player.getBalance());  // Update the balance label
    }

    private void resetBetOptions() {
        remove(betPanel);
        add(buttonPanel, BorderLayout.SOUTH);
        revalidate();
        repaint();
    }
    

    public void startGame() {
        roundResults.clear();
        resetBet();
        roundCount = 1;
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
        statusLabel.setText("Round " + roundCount + ": Your move!");
    }

    private void updateBalance(boolean result) {
        player.updateBalanceAfterBet(result);
        updateBalanceDisplay();
    }
    

    private void resetBet() {
        bet = 0;
        betField.setText("Enter Bet Amount");
    }

    private void hit() {
        playerHand.add(deck.draw());
        if (calculateHandValue(playerHand) > 21) {
            statusLabel.setText("You busted! Dealer wins.");
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
            continueButton.setEnabled(true);
        }
        blackjackCheck();
        updateAreas();
    }

    private void stand() {
        while (calculateHandValue(dealerHand) < 12 || calculateHandValue(playerHand) > calculateHandValue(dealerHand)) {
            dealerHand.add(deck.draw());
        }
        revealDealerHoleCard();
        determineWinner();
        updateAreas();
    }

    private void blackjackCheck() {
        if (calculateHandValue(playerHand) == 21) {
            statusLabel.setText("Blackjack! You win!");
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
            continueButton.setEnabled(true);
            boolean result = true;
            updateBalance(result);
        }
    }

    private void determineWinner() {
        int playerValue = calculateHandValue(playerHand);
        int dealerValue = calculateHandValue(dealerHand);
        boolean result = false;
        String winner;
        int balanceChange = 0; // This will track the balance change for this round
       
        if (playerValue > 21) {
            statusLabel.setText("You busted! Dealer wins.");
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
            continueButton.setEnabled(true);
            result = false;
            winner = "Dealer";
            updateBalance(false);
        } else if (dealerValue > 21) {
            statusLabel.setText("Dealer busted! You win!");
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
            continueButton.setEnabled(true);
            result = true;
            winner = "Player";
            updateBalance(true);
        } else if (playerValue > dealerValue) {
            statusLabel.setText("You win!");
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
            continueButton.setEnabled(true);
            result = true;
            winner = "Player";
            updateBalance(true);
        } else if (playerValue < dealerValue) {
            statusLabel.setText("Dealer wins!");
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
            continueButton.setEnabled(true);
            
            result = false;
            winner = "Dealer";
            updateBalance(false);
        } else {
            statusLabel.setText("Push!");
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
            continueButton.setEnabled(true);
            
            winner = "Push";
            updateBalance(true);
        }
    
        roundResults.add(new String[]{
            String.valueOf(roundCount),
            String.valueOf(playerValue),
            String.valueOf(dealerValue),
            winner
        });
        balanceChanges.add(balanceChange); // Add the balance change for this round
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
        dealerArea.setText("Dealer's Hand:\n" + handToString(dealerHand) + "\nValue: " + dealerHand.get(0).getValue());
        updateBalanceDisplay();
    }

    private void revealDealerHoleCard() {
        dealerArea.setText("Dealer's Hand:\n" + handToString(dealerHand) + "\nValue: " + calculateHandValue(dealerHand));
    }

    private String handToString(List<Card> hand) {
        StringBuilder sb = new StringBuilder();
        int cardCount = hand.size();
        
        // Display the card as a visual representation in a grid-like format
        for (int i = 0; i < cardCount; i++) {
            Card card = hand.get(i);
            String cardString = formatCard(card);

            if (dealerHand.contains(card) && i == 1) {
                // Hide the dealer's second card
                cardString = "?????\n?-----?\n?-----?\n?????";
            }
            
            sb.append(cardString);
            if (i < cardCount - 1) {
                sb.append("\n"); // Add a line break between cards
            }
        }
        return sb.toString();
    }

    private String formatCard(Card card) {
        // Represent the card as a string that visually looks like a card.
        String rank = card.getValueStr(); // Get value (Ace, 2, 3, ..., King)
        String suit = card.getSuit(); // Get suit (Hearts, Diamonds, Clubs, Spades)
        
        String cardTopBottom = "+-----+";
        String cardMiddle = " |  " + rank + "   |"; // Rank in the middle of the card
        
        // Adjust for the suit representation
        String cardSuitLine = " |  " + suit.charAt(0) + "   |"; // First letter of suit (H, D, C, S)
    
        // Combine the card lines into a card shape
        return cardTopBottom + "\n" + cardMiddle + "\n" + cardSuitLine + "\n" + cardTopBottom;
    }

    private void nextRound(){
        if (roundCount >= 5) {
            statusLabel.setText("Game Over! Thanks for playing.");
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
            continueButton.setEnabled(false);
            betButton.setEnabled(false);
            return;
        }
        roundCount++;
        playerHand.clear();
        dealerHand.clear();
        deck.shuffle();
        playerHand.add(deck.draw());
        playerHand.add(deck.draw());
        dealerHand.add(deck.draw());
        dealerHand.add(deck.draw());
        hitButton.setEnabled(true);
        standButton.setEnabled(true);
        continueButton.setEnabled(false);
        updateAreas();
        statusLabel.setText("Round " + roundCount + ": Your move!");
    }

    

}