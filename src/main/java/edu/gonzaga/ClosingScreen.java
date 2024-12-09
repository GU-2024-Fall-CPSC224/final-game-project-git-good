package edu.gonzaga;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ClosingScreen extends JPanel {

    private JButton playButton;
    private JLabel resultsText;
    private JTable resultsTable;
    private JScrollPane scrollPane;

    public ClosingScreen(GUIManager manager) {
        // Set layout for the panel
        setLayout(new BorderLayout());

        // Initialize components
        playButton = new JButton("Play Again");
        resultsText = new JLabel("Game Results and Statistics");
        resultsText.setFont(new Font("Arial", Font.BOLD, 18));
        resultsText.setHorizontalAlignment(JLabel.CENTER);

        JPanel buttonPanel = genButtonPanel();
        JPanel statsPanel = genStatisticsPanel();

        // Add components to the layout
        add(resultsText, BorderLayout.NORTH);
        add(statsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add ActionListener to playButton
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Switching to StartScreen");
                manager.showScreen("StartScreen");
            }
        });
    }

    private JPanel genStatisticsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        // Column names for the table
        String[] columnNames = {"Round", "Player Score", "Dealer Score", "Round Winner", "Balance Change"};
        
        // Example data for several rounds of Blackjack
        Object[][] data = {
            {"1", "x", "y", "Name", "$0"},
            {"2", "x", "y", "Name", "$0"},
            {"3", "x", "y", "Name", "$0"},
            {"4", "x", "y", "Name", "$0"},
            {"5", "x", "y", "Name", "$0"},
        };
        
        // Create table model with data and column names
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        
        // Create JTable with the model
        resultsTable = new JTable(tableModel);
        
        // Set some table properties for better appearance
        resultsTable.setFont(new Font("Arial", Font.PLAIN, 14));
        resultsTable.setRowHeight(30);
        resultsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        resultsTable.getTableHeader().setBackground(Color.LIGHT_GRAY);
        resultsTable.setSelectionBackground(Color.CYAN);
        
        // Add the table to a scroll pane
        scrollPane = new JScrollPane(resultsTable);
        panel.add(scrollPane, BorderLayout.CENTER);
    
        return panel;
    }
    

    private JPanel genButtonPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(playButton);
        return panel;
    }
    
    public void updateTableData(List<String[]> roundResults, List<Integer> balanceChanges) {
        DefaultTableModel tableModel = (DefaultTableModel) resultsTable.getModel();
        tableModel.setRowCount(0); // Clear existing rows
        for (int i = 0; i < roundResults.size(); i++) {
            String[] result = roundResults.get(i);
            int balanceChange = balanceChanges.get(i); // Balance change for this round
            String balanceChangeStr = (balanceChange >= 0) ? "$" + balanceChange : "-$" + Math.abs(balanceChange); // Format balance change
            tableModel.addRow(new Object[]{
                result[0],        // Round
                result[1],        // Player Score
                result[2],        // Dealer Score
                result[3],        // Round Winner
                balanceChangeStr  // Balance Change
            });
        }
    }  

    public void clearTableData() {
        DefaultTableModel tableModel = (DefaultTableModel) resultsTable.getModel();
        tableModel.setRowCount(0);
    }

}