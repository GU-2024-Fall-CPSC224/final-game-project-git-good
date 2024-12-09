package edu.gonzaga;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

// Blackjack Game Class
public final class BlackjackGame extends JPanel {
    private final JTextArea playerArea;
    private final JTextArea dealerArea;
    private final JButton hitButton;
    private final JButton standButton;
    private final JButton betButton = new JButton("Place Bet");
    private final JLabel statusLabel;
    private final Deck deck;
    private final List<Card> playerHand;
    private final List<Card> dealerHand;
    private final JTextField betField;
    private final Player player;

    private final JButton continueButton = new JButton("Next Round");
    private int roundCount;
    private List<String[]> roundResults = new ArrayList<>();

    private final JLabel balanceLabel = new JLabel("Balance: $1000", SwingConstants.CENTER);
    private final JPanel buttonPanel;
    private final JPanel betPanel;
    private int bet;

    private final BackgroundPanel playerPanel;
    private final BackgroundPanel dealerPanel;
    

    private boolean dealerCardRevealed = false;

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

        JPanel gamePanel = new JPanel(new GridLayout(2, 1));
        playerPanel = new BackgroundPanel("/bjtable.jpg");
        playerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        dealerPanel = new BackgroundPanel("/bjtable.jpg");
        dealerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5)); // Maintain horizontal alignment
        add(gamePanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.NORTH);
        
        gamePanel.add(new JScrollPane(dealerPanel)); // Dealer's hand
        gamePanel.add(new JScrollPane(playerPanel)); // Player's hand
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
        bet = 0;
        roundCount = 1;
        startGame();

        // Button actions
        hitButton.addActionListener(e -> hit());
        standButton.addActionListener(e -> stand());
        betButton.addActionListener(e -> showBetOptions());
        restartButton.addActionListener(e -> startGame());
        continueButton.addActionListener(e -> nextRound());
        endButton.addActionListener(e -> {
            manager.setClosingScreenData(roundResults, balanceChanges);
            manager.showScreen("ClosingScreen");
        });
        
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
        revealDealerHoleCard();
    
        while (calculateHandValue(dealerHand) < 17 || 
               (calculateHandValue(dealerHand) <= calculateHandValue(playerHand) && calculateHandValue(playerHand) <= 21)) {
            dealerHand.add(deck.draw());
        }
    
        if (calculateHandValue(dealerHand) > 21) {
            revealDealerHoleCard(); 
        }
        
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

    private String determineWinner() {
        revealDealerHoleCard();
    
        int playerValue = calculateHandValue(playerHand);
        int dealerValue = calculateHandValue(dealerHand);
        boolean result;
        String winner;
    
        if (playerValue > 21) {
            statusLabel.setText("You busted! Dealer wins.");
            result = false;
            winner = "Dealer";
        } else if (dealerValue > 21) {
            statusLabel.setText("Dealer busted! You win!");
            result = true;
            winner = "Player";
            continueButton.setEnabled(true);
        } else if (playerValue > dealerValue) {
            statusLabel.setText("You win!");
            result = true;
            winner = "Player";
        } else if (playerValue < dealerValue) {
            statusLabel.setText("Dealer wins!"); 
            winner = "Dealer";
            result = false;
        } else {
            statusLabel.setText("Push!");
            winner = "Push";
            result = false;
        }
    
        roundResults.add(new String[]{
            String.valueOf(roundCount),
            String.valueOf(playerValue),
            String.valueOf(dealerValue),
            winner
        });
    
        updateBalance(result);
        hitButton.setEnabled(false);
        standButton.setEnabled(false);
        continueButton.setEnabled(true);

        return winner;
    }
    

    private int calculateHandValue(List<Card> hand) {
        int totalValue = 0;
        int aceCount = 0;
    
        // Calculate total value and count the number of aces
        for (Card card : hand) {
            totalValue += card.getValue();
            if (card.toString().contains("A")) {  // Assuming Ace has "A" in its string representation
                aceCount++;
            }
        }
    
        // Adjust the value of aces (only reduce if totalValue exceeds 21)
        while (totalValue > 21 && aceCount > 0) {
            totalValue -= 10;
            aceCount--;
        }
    
        // Ensure that if total is 21 and has an Ace counted as 11, it remains 21
        if (totalValue == 21 && aceCount == 1) {
            return 21;  // Return 21 immediately if player has an Ace valued at 11
        }
    
        return totalValue;
    }
    

    private JLabel createCardLabel(Card card) {
        String imagePath = card.getImagePath();
        ImageIcon originalIcon = new ImageIcon(imagePath);
    
        // Define consistent dimensions
        int targetWidth = 50;
        int targetHeight = 75;
    
        // Ensure consistent aspect ratio
        Image scaledImage = originalIcon.getImage().getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
    
        return new JLabel(scaledIcon);
    }
    

    private JLabel createBackCardLabel() {
        String backCardPath = "PNG-cards-1.3/back.png";
        ImageIcon backIcon = new ImageIcon(backCardPath);
    
        // Define consistent dimensions
        int targetWidth = 85;
        int targetHeight = 93;
    
        // Ensure consistent aspect ratio
        Image scaledBackImage = backIcon.getImage().getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledBackIcon = new ImageIcon(scaledBackImage);
    
        return new JLabel(scaledBackIcon);
    }
    
    
    

    private void updateAreas() {
        playerPanel.removeAll();
        dealerPanel.removeAll();
        
        // Add player's cards
        for (Card card : playerHand) {
            playerPanel.add(createCardLabel(card));
        }
        JLabel playerValueLabel = new JLabel("Player Points: " + calculateHandValue(playerHand));
        playerPanel.add(playerValueLabel);
    
        // Add dealer's cards
        int dealerHandValue = 0;
        for (int i = 0; i < dealerHand.size(); i++) {
            if (i == 1 && !dealerCardRevealed) {
                dealerPanel.add(createBackCardLabel());
            } else {
                dealerPanel.add(createCardLabel(dealerHand.get(i)));
                dealerHandValue += dealerHand.get(i).getValue();
            }
        }
        JLabel dealerValueLabel = new JLabel("Dealer Points: " + 
            (dealerCardRevealed ? calculateHandValue(dealerHand) : dealerHandValue));
        dealerPanel.add(dealerValueLabel);
    
        // Refresh panels
        playerPanel.revalidate();
        playerPanel.repaint();
        dealerPanel.revalidate();
        dealerPanel.repaint();
    
        updateBalanceDisplay();
    }
    
    
    
    

    private void revealDealerHoleCard() {
        dealerCardRevealed = true;
        updateAreas();
    }

    private void hideDealerHoleCard() {
        dealerCardRevealed = false;
        updateAreas();
    }
    
    private void nextRound(){
        hideDealerHoleCard();
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

        String[] result = new String[] {
            String.valueOf(roundCount), // Round number
            String.valueOf(calculateHandValue(playerHand)), // Player score
            String.valueOf(calculateHandValue(dealerHand)), // Dealer score
        };
        roundResults.add(result);
    }
}