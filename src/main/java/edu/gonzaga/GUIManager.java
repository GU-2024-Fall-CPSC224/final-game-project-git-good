package edu.gonzaga;

import java.awt.CardLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUIManager {

    private JFrame mainFrame;
    private CardLayout cardLayout;
    private JPanel container;

    // Keep explicit references to each screen
    private StartScreen startScreen;
    private BlackjackGame blackjackGame;
    private ClosingScreen closingScreen;

    public GUIManager() {
        
        mainFrame = new JFrame("Blackjack");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);

        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        // Initialize screens
        startScreen = new StartScreen(this);
        blackjackGame = new BlackjackGame(this);
        closingScreen = new ClosingScreen(this);

        // Add screens to container
        container.add(startScreen, "StartScreen");
        container.add(blackjackGame, "BlackjackGame");
        container.add(closingScreen, "ClosingScreen");

        mainFrame.add(container);
        mainFrame.setVisible(true);
    }

    public void showScreen(String screenName) {
        cardLayout.show(container, screenName);
    }

    public void setClosingScreenData(List<String[]> roundResults) {
        closingScreen.clearTableData();
        closingScreen.updateTableData(roundResults);
    }

    // Method to get a screen by its name
    public JPanel getScreen(String screenName) {
        switch (screenName) {
            case "StartScreen":
                return startScreen;
            case "BlackjackGame":
                return blackjackGame;
            case "ClosingScreen":
                return closingScreen;
            default:
                return null; // Or throw an exception if needed
        }
    }
}
