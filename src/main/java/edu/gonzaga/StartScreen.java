package edu.gonzaga;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartScreen extends JPanel {

    private JButton playButton;
    private JPanel playerPanel;
    private JPanel playPanel;

    public StartScreen(GUIManager manager) {
        // Set layout for the panel
        setLayout(new BorderLayout());
        
        // Initialize components
        playButton = new JButton("Play");
        playPanel = genPlayPanel();
        playerPanel = genPlayerPanel();

        // Add panels to layout
        add(playPanel, BorderLayout.CENTER);
        add(playerPanel, BorderLayout.SOUTH);

        // Add ActionListener to playButton
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Switching to BlackjackGame");
                manager.showScreen("BlackjackGame");
            }
        });
    }

    private JPanel genPlayPanel() {
        JPanel newPanel = new JPanel();
        newPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        newPanel.add(playButton);
        return newPanel;
    }

    private JPanel genPlayerPanel() {
        JPanel newPanel = new JPanel();
        newPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        newPanel.setLayout(new GridLayout(1, 4, 5, 5));
        
        JLabel playerCountLabel = new JLabel("Player count:");
        newPanel.add(playerCountLabel);

        for (int i = 1; i <= 4; i++) {
            String buttonText = (i == 1) ? i + " player" : i + " players";
            JButton playerCountButton = new JButton(buttonText);
            newPanel.add(playerCountButton);
        }

        return newPanel;
    }
}