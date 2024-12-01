package edu.gonzaga;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class StartScreen {

    private JFrame mainWindowFrame;
    private JPanel controlPanel;
    private JPanel scorecardPanel;

    // Dice view, user input, reroll status, and reroll button
    private JTextField diceValuesTextField;
    private JTextField diceKeepStringTextField;
    private JButton diceRerollBtn;
    private JTextField rerollsLeftTextField;

    // Player name - set it to your choice
    private JTextField playerNameTextField = new JTextField();

    // Buttons for showing dice and checkboxes for meld include/exclude
    ArrayList<JButton> diceButtons = new ArrayList<>();
    ArrayList<JCheckBox> meldCheckboxes = new ArrayList<>();

    private JButton calcMeldButton = new JButton("Calc Meld");
    private JTextField diceDebugLabel = new JTextField();
    private JLabel meldScoreTextLabel = new JLabel();
    private JButton rollButton = new JButton();

    private JPanel playerInfoPanel = new JPanel();
    private JPanel diceControlPanel = new JPanel();
    private JPanel meldControlPanel = new JPanel();

    public StartScreen() {
        setupGUI();
    }

    void setupGUI() {
        // Make and configure the window itself
        this.mainWindowFrame = new JFrame("Simple GUI Farkle");
        this.mainWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainWindowFrame.setLocation(100, 100);

        // Player info and roll button panel
        this.playerInfoPanel = genPlayerInfoPanel();

        // Dice status and checkboxes to show the hand and which to include in the meld
        this.diceControlPanel = genDiceControlPanel();

        // The bottom Meld control panel
        this.meldControlPanel = genMeldControlPanel();

        mainWindowFrame.getContentPane().add(BorderLayout.NORTH, this.playerInfoPanel);
        mainWindowFrame.getContentPane().add(BorderLayout.CENTER, this.diceControlPanel);
        mainWindowFrame.getContentPane().add(BorderLayout.SOUTH, this.meldControlPanel);
        mainWindowFrame.pack();
    }

    private JPanel genMeldControlPanel() {
        JPanel newPanel = new JPanel();
        newPanel.setLayout(new FlowLayout());

        JLabel meldScoreLabel = new JLabel("Meld Score:");
        this.meldScoreTextLabel.setText("0");
        
        newPanel.add(calcMeldButton);
        newPanel.add(meldScoreLabel);
        newPanel.add(this.meldScoreTextLabel);

        return newPanel;
    }


    /**
     * Generates and returns a JPanel containing components for dice control.
     *
     * This method creates a JPanel with a black border and a grid layout (3 rows, 7 columns).
     * It includes components such as labels for dice values and meld options, buttons for each
     * dice, and checkboxes for melding. The dice buttons and meld checkboxes are added to
     * corresponding lists for further manipulation.
     *
     * @return A JPanel containing components for dice control.
     */
    private JPanel genDiceControlPanel() {
        JPanel newPanel = new JPanel();
        newPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        newPanel.setLayout(new GridLayout(3, 7, 1, 1));
        JLabel diceLabel = new JLabel("Dice Vals:");
        JLabel meldBoxesLabel = new JLabel("Meld 'em:");

        newPanel.add(new Panel());  // Upper left corner is blank
        for(Integer index = 0; index < 6; index++) {
            //JLabel colLabel = new JLabel(index.toString(), SwingConstants.CENTER);
            JLabel colLabel = new JLabel(Character.toString('A' + index), SwingConstants.CENTER);
            newPanel.add(colLabel);
        }
        newPanel.add(diceLabel);

        for(Integer index = 0; index < 6; index++) {
            JButton diceStatusButton = new JButton("D" + index.toString());
            this.diceButtons.add(diceStatusButton);
            newPanel.add(diceStatusButton);
        }

        newPanel.add(meldBoxesLabel);
        for(Integer index = 0; index < 6; index++) {
            JCheckBox meldCheckbox = new JCheckBox();
            meldCheckbox.setHorizontalAlignment(SwingConstants.CENTER);
            this.meldCheckboxes.add(meldCheckbox);
            newPanel.add(meldCheckbox);
        }

        return newPanel;
    }

    /**
     * Generates and returns a JPanel containing player information components.
     *
     * This method creates a JPanel with a black border and a horizontal flow layout.
     * It includes components such as a JLabel for player name, a JTextField for entering
     * the player name, a JButton for rolling dice, and a debug label for dice information.
     * The player name text field, dice debug label, and roll button are added to the panel
     * with appropriate configurations.
     *
     * @return A JPanel containing components for player information.
     */
    private JPanel genPlayerInfoPanel() {
        JPanel newPanel = new JPanel();
        newPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        newPanel.setLayout(new FlowLayout());    // Left to right

        JLabel playerNameLabel = new JLabel("Player name:");
        playerNameTextField.setColumns(20);
        diceDebugLabel.setColumns(6);
        diceDebugLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the text
        rollButton.setText("Roll Dice");


        newPanel.add(playerNameLabel);   // Add our player label
        newPanel.add(playerNameTextField); // Add our player text field
        newPanel.add(rollButton);        // Put the roll button on there
        newPanel.add(this.diceDebugLabel);

        return newPanel;
    }


    /*
     *  This is a method to show you how you can set/read the visible values
     *   in the various text widgets.
     */
    private void putDemoDefaultValuesInGUI() {
        // Example setting of player name
        this.playerNameTextField.setText("Player One");

        // Example Dice debug output
        this.diceDebugLabel.setText("124566");
    }

    /*
     * This is a demo of how to add callbacks to the buttons
     *  These callbacks can access the class member variables this way
     */
    private void addDemoButtonCallbackHandlers() {
        // Example of a button callback - just prints when clicked
        this.rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("They clicked the roll button!");
                diceButtons.get(2).setText("Rolled");
                diceDebugLabel.setText("67321");
            }
        });

        // Example of another button callback
        // Reads the dice checkboxes and counts how many are checked (selected)
        // Sets the meldScoreTextLabel to how many of the checkboxes are checked
        this.calcMeldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer boxCheckCount = 0;
                for(JCheckBox checkBox : meldCheckboxes) {
                    if(checkBox.isSelected()) {
                        boxCheckCount++;
                    }
                }
                System.out.println("Setting meld score text");
                meldScoreTextLabel.setText(boxCheckCount.toString());
            }
        });

        // Example of a checkbox handling events when checked/unchecked
        JCheckBox boxWithEvent = this.meldCheckboxes.get(1);
        boxWithEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(boxWithEvent.isSelected()) {
                    System.out.println("Checkbox is checked");
                } else {
                    System.out.println("Checkbox is unchecked");
                }
            }
        });
    }

    /*
     *  Builds the GUI frontend and allows you to hook up the callbacks/data for game
     */
    void runGUI() {
        System.out.println("Starting GUI app");
        setupGUI();

        // These methods are to show you how it works
        // Once you get started working, you can comment them out so they don't
        //  mess up your own code.
        putDemoDefaultValuesInGUI();
        addDemoButtonCallbackHandlers();

        // Right here is where you could methods to add your own callbacks
        // so that you can make the game actually work.

        // Run the main window - begins GUI activity
        mainWindowFrame.setVisible(true);
        System.out.println("Done in GUI app");
    }
}