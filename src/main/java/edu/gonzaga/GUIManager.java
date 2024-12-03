package edu.gonzaga;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUIManager {

    private JFrame mainFrame;
    private CardLayout cardLayout;
    private JPanel container;

    public GUIManager() {
        mainFrame = new JFrame("Blackjack");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        

        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        // Add screens
        container.add(new StartScreen(this), "StartScreen");
        container.add(new BlackjackGame(this), "BlackjackGame");

        mainFrame.add(container);
        mainFrame.setVisible(true);
    }

    public void showScreen(String screenName) {
        cardLayout.show(container, screenName);
    }
}