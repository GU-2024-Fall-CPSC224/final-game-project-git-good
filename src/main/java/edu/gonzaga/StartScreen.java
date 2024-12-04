package edu.gonzaga;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartScreen extends JPanel {

    private JButton playButton;

    public StartScreen(GUIManager manager) {
        // Set layout for the panel
        setLayout(new BorderLayout());

        // Initialize components
        JLabel welcomeLabel = new JLabel("Welcome to Blackjack!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        playButton = new JButton("Start Game");
        playButton.setFont(new Font("Arial", Font.BOLD, 16));
        playButton.setBackground(Color.GREEN);
        playButton.setForeground(Color.WHITE);

        JLabel copyrightLabel = new JLabel("© 2024 Tyler Yasukochi, Parker Shore, Jonathan Courter.");
        copyrightLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        copyrightLabel.setHorizontalAlignment(JLabel.CENTER);
        copyrightLabel.setForeground(Color.GRAY);
        copyrightLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));  // Add bottom padding

        

        // Add ActionListener to playButton
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Switching to BlackjackGame");
                manager.showScreen("BlackjackGame");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPanel.add(playButton);

        // Add components to layout
        add(welcomeLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(copyrightLabel, BorderLayout.SOUTH);
    }
}
