/*
 * Final project main driver class
 * 
 * 
 * Project Description: Digital version of Blackjack
 * 
 * 
 * Contributors: Tyler Yasukochi, Parker Shore, Jonathan Courter
 * 
 * 
 * Copyright: 2024
 */
package edu.gonzaga;

import javax.swing.SwingUtilities;

/** Main program class for launching your team's program. */
public class Blackjack {
    public static void main(String[] args) {

        PlayerList players = new PlayerList();

        Blackjack game = new Blackjack();
        SwingUtilities.invokeLater(() -> {
            StartScreen startScreen = new StartScreen();
            startScreen.runGUI();
        });

    }
}
