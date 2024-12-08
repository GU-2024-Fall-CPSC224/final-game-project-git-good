package edu.gonzaga;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartScreen extends JPanel {

    private final JButton playButton;
    private BufferedImage backgroundImage;

    public StartScreen(GUIManager manager) {
        // Load the background image
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/bjwelcomescreen.jpg"));
        } catch (IOException e) {
            System.out.println("Error: Unable to load background image.");
        }


        // Set layout for the panel
        setLayout(new BorderLayout());

        // Initialize components
        JLabel welcomeLabel = new JLabel("Welcome to Blackjack! \u2660 \u2665 \u2666 \u2663");
        
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        welcomeLabel.setForeground(Color.BLACK);

        playButton = new JButton("Start Game");
        playButton.setFont(new Font("Arial", Font.BOLD, 16));
        playButton.setBackground(Color.GREEN);
        playButton.setForeground(Color.WHITE);

        JLabel copyrightLabel = new JLabel("© 2024 Tyler Yasukochi, Parker Shore, Jonathan Courter.");
        copyrightLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        copyrightLabel.setHorizontalAlignment(JLabel.CENTER);
        copyrightLabel.setForeground(Color.GRAY);
        copyrightLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        //copyrightLabel.setOpaque(false); // Make the label transparent
        copyrightLabel.setForeground(Color.BLACK);

        // Add ActionListener to playButton
        playButton.addActionListener((ActionEvent e) -> {
            System.out.println("Switching to BlackjackGame");
            manager.showScreen("BlackjackGame");
            ((BlackjackGame) manager.getScreen("BlackjackGame")).startGame();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPanel.setOpaque(false); // Make the panel transparent
        buttonPanel.add(playButton);

        // Add components to layout
        add(welcomeLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(copyrightLabel, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
