package edu.gonzaga;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is primarily responsible for the GUI aspect of the Yahtzee game.
 *
 * @version v1.0
 */

public class YahtzeeGUI extends JFrame implements ItemListener {
    /**
     * game represents the game object that controls how the game is played
     */
    private Game game;
    /**
     * dieImages is an array of DieLabels that hold the images corresponding to the die values
     */
    private ArrayList<DieLabel> dieImages = new ArrayList<>();
    /**
     * checkBoxed is an array of check boxes used to figure out which die to roll and keep
     */
    private ArrayList<JCheckBox> checkBoxed = new ArrayList<>();
    /**
     * keepDieArray is an array of characters that keep track of whether or not a checkbox is clicked
     */
    private ArrayList<Character> keepDieArray = new ArrayList<>();
    /**
     * scoreLabels is an arraylist of scorecardlabels that hold the score card values
     */
    private ArrayList<ScoreCardLabel> scoreLabels = new ArrayList<>();
    /**
     * turn holds the value of the turn the player is on
     */
    private int turn = 1;
    /**
     * turnOver keeps track of whether or not the player's 3 turns are up
     */
    private boolean turnOver;
    /**
     * f is the frame that contains the GUI
     */
    JFrame f = new JFrame();

    // testing
    // using int for initializing game array
    private int playerNum = 1;

    /**
     * Constructor for the YahtzeeGUI class, sets frame title, size, and default close settings
     *
     */
    public YahtzeeGUI() {
        f.setTitle("Yahtzee");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(700, 700);
    }

    /**
     * This method begins running the Yahtzee game by getting the game configs setup, constructing the game object, and calling
     * playDiceTurn() to begin the game for the user.
     *
     * No params
     * No returns
     */
    public void runYahtzee(){
        f.setSize(1200, 700);
        f.setLayout(new BorderLayout(5,5));

        //add title and instructions
        this.addNorthPanel();

        //panel for player info
        JPanel playersPanel = new JPanel(new FlowLayout());
        //add labels and combo box to panel
        JLabel numPlayersLabel = new JLabel("How many players are there?");
        JComboBox numPlayersBox = new JComboBox();
        numPlayersBox.addItem(1);
        numPlayersBox.addItem(2);
        numPlayersBox.addItem(3);
        numPlayersBox.addItem(4);
        numPlayersBox.addItem(5);
        numPlayersBox.addItem(6);
        numPlayersBox.addItem(7);
        numPlayersBox.addItem(8);
        JButton enterNamesButton = new JButton("Enter Player Names");
        playersPanel.add(numPlayersLabel);
        playersPanel.add(numPlayersBox);
        playersPanel.add(enterNamesButton);
        f.add(playersPanel, BorderLayout.CENTER);


        // Stores the names of players entered in the text fields to be passed into the Game class
        ArrayList<String> playerNamesList = new ArrayList<String>();

        //add button to play game
        JButton playGameButton = new JButton("Play Game");
        playGameButton.setFont(new Font("Arial", Font.BOLD, 25));
        playGameButton.addActionListener(e -> {
            game = new Game(new Player[playerNum], playerNamesList);
            this.updateScoreCard(); //create the scoreLabels
            //clear the frame
            f.getContentPane().removeAll();
            f.repaint();
            turnOver = false;
            //begin turn
            playDiceTurn();
        });
        
        JPanel southPanel = new JPanel();
        southPanel.add(playGameButton);
        f.add(southPanel, BorderLayout.SOUTH);
        southPanel.setVisible(false);

        //add action listener for enter names button to pull up text fields
        enterNamesButton.addActionListener(e -> {
            int numPlayers = (Integer) numPlayersBox.getSelectedItem();
            // testing
            // used for when initializing game with correct array size
            playerNum = (Integer) numPlayersBox.getSelectedItem();

            // clear player selection screen
            playersPanel.removeAll();
            playersPanel.revalidate();

            // testing
            JPanel namesPanel = new JPanel();
            namesPanel.setLayout(new BoxLayout(namesPanel, BoxLayout.Y_AXIS));

            JLabel enterNamesLabel = new JLabel("Enter Player Names:");
            enterNamesLabel.setFont(new Font("Arial", Font.BOLD, 18));
            namesPanel.add(enterNamesLabel);

            JLabel[] playerNameLabels = new JLabel[numPlayers];
            JTextField[] playerNameFields = new JTextField[numPlayers];
            for (int i = 0; i < numPlayers; i++){
                int j = i + 1;
                playerNameLabels[i] = new JLabel("Player " + j + "'s Name:");
                playerNameFields[i] = new JTextField("Player " + j);
                playerNameFields[i].setColumns(15);
                namesPanel.add(playerNameLabels[i]);
                namesPanel.add(playerNameFields[i]);
            }

            // Assigns the names entered in text field to ArrayList of playerNames
            JButton setNamesButton = new JButton("Set Player Names");
            setNamesButton.addActionListener(ee -> {
                playerNamesList.clear();
                for (int i = 0; i < playerNameFields.length; i++) {
                    playerNamesList.add(playerNameFields[i].getText());
                }

                // Unit test: ensures player names get added correctly to ArrayList
                for (int i = 0; i < playerNamesList.size(); i++) {
                    System.out.println(playerNamesList.get(i));
                }
                southPanel.setVisible(true);

            });

            playersPanel.add(namesPanel);
            playersPanel.add(setNamesButton);
            playersPanel.revalidate();
        });
        f.setVisible(true);

    }

    /**
     * This method adds a panel to the top of the opening frame, and has specified borders and a box layout.
     * The panel includes the yahtzee game logo image icon, centered, and game instructions text area
     * with text wrapped by word. (Instructions can also be updated if needed)
     * and centers the image.
     */
    public void addNorthPanel(){
        JPanel topPanel = new JPanel();
        topPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        ImageIcon titleLogo = new ImageIcon("YahtzeeMedia-2/Logos/bigGreen.png");
        JLabel yahtzeeGameTitle = new JLabel(titleLogo);
        yahtzeeGameTitle.setBorder(new EmptyBorder(0, 0, 5, 0));
        topPanel.add(yahtzeeGameTitle);
        yahtzeeGameTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        //instructions
        String instructions = "GAME INSTRUCTIONS: Select the number of players, and click \"Enter Player Names\" to " +
                "enter your player names. Once ready, click \"Play Game\" to start the game."  +
                "On their turn, each player will have 3 chances to roll their hand of 5 dice, selecting dice to roll again as they go." +
                " Possible scores for that hand will be displayed, then the current player can choose the score they want to add " +
                "to their cumulative scorecard. Turns will continue this way until all scorecards are full. " +
                "The winner will be announced at the end of the game! \n";
        JTextArea instructionsTextArea = new JTextArea(instructions);
        instructionsTextArea.setLineWrap(true);
        instructionsTextArea.setWrapStyleWord(true);
        instructionsTextArea.setEditable(false);
        instructionsTextArea.setBorder(new LineBorder(Color.RED, 2));
        topPanel.add(instructionsTextArea);
        f.add(topPanel, BorderLayout.NORTH);
    }

    /**
     * This method updates the scoreLabels array by pulling the most recent version of the array from the player's scorecard.
     *
     * No params
     * No returns
     */
    public void updateScoreCard(){
        scoreLabels = game.getPlayer().getScore().getLabels(); //get newest score card
        for(int i = 0; i < scoreLabels.size(); i++){
            scoreLabels.get(i).setText(scoreLabels.get(i).getText());
        }
    }

    /**
     * This method outputs the scorecard to the frame including the section totals and bonuses.
     *
     * No params
     * No returns
     */
    public void showScoreCard(){
        //create panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        //panel for player name
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel scorecardTitle = new JLabel("SCORECARD: "+ game.getPlayerName());
        scorecardTitle.setFont(scorecardTitle.getFont ().deriveFont (18.0f));
        panel.add(scorecardTitle);

        //panel for scorecard header
        JPanel header = new JPanel();
        JLabel label = new JLabel("Line             Score");
        panel.add(label);

        //calculate totals and bonus:
        int upperTotal = game.getPlayer().getScore().getUpperTotal();
        int bonus = 0;
        if(Integer.valueOf(upperTotal) >= 63){
            bonus = 35;
        }
        int lowerTotal = game.getPlayer().getScore().getLowerScore();
        int grandTotal = upperTotal + lowerTotal + bonus;

        //add each score to a JLabel
        for(int i = 0; i < scoreLabels.size(); i++){
            if(scoreLabels.get(i).returnScoreCode() == "3 of a Kind"){ //add totals before lower section
                panel.add(new JLabel("----------------"));
                panel.add(new JLabel("Sub Total:            " + upperTotal));
                panel.add(new JLabel("Bonus                  " + bonus));
                panel.add(new JLabel("----------------"));
                panel.add(new JLabel("Upper Total         " + (upperTotal + bonus)));
                panel.add(scoreLabels.get(i));
            }
            else if (scoreLabels.get(i).returnScoreCode() == "Chance"){ //add totals after lower section
                panel.add(scoreLabels.get(i));
                panel.add(new JLabel("----------------"));
                panel.add(new JLabel("Lower total           " + lowerTotal));
                panel.add(new JLabel("----------------"));
                panel.add(new JLabel("GRAND TOTAL          " + grandTotal));
            }
            else {
                panel.add(scoreLabels.get(i));
            }
        }
        // testing
        mainPanel.add(header);
        mainPanel.add(panel);
        mainPanel.setBorder(new EmptyBorder(6, 6, 6, 6));
        f.add(mainPanel, BorderLayout.WEST);
        f.repaint();
        f.setVisible(true);
    }

    /**
     * This method runs a player's turn by allowing the player to roll three times or until they want to keep all of their die
     *
     * No params
     * No returns
     */
    public void playDiceTurn(){
        this.createDieImages(); //create starting die labels and add to frame
        this.rollNewDie(); //reset die in between turns

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 1, 5, 5));

        turn++;

        JLabel playerNameLabel = new JLabel(game.getPlayerName() + "'s Turn", SwingConstants.CENTER);
        playerNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        playerNameLabel.setOpaque(true);
        playerNameLabel.setBackground(Color.GREEN);

        JTextArea instructionsLabel = new JTextArea("Select the dice you want to reroll, " +
                "then click the button to roll them again.");
        // instructionsLabel.setMaximumSize(new Dimension(200, 50));
        instructionsLabel.setBounds(50, 100, 200, 30);
        instructionsLabel.setLineWrap(true);
        instructionsLabel.setWrapStyleWord(true);
        instructionsLabel.setEditable(false);


        JButton rollDie = new JButton("Reroll selected dice"); //create button
        rollDie.setFont(new Font("Arial", Font.BOLD, 20));
        rollDie.setBounds(50, 100, 200, 30);


        //when roll die is clicked
        rollDie.addActionListener(new ActionListener() { //listener
            @Override
            public void actionPerformed(ActionEvent e) {
                //send keepDieArray to game to update and roll the hand
                ArrayList<Die> newDieList = game.playTurn(keepDieArray, turn);
                if (turn < 3 && !(game.getToKeepGoal().equals(keepDieArray))) { //while less than 3 turns
                    for (int i = 0; i < dieImages.size(); i++) {
                        dieImages.get(i).setIcon(newDieList.get(i).getValue()); //update images
                        checkBoxed.get(i).setSelected(false); //reset checkboxes
                        keepDieArray.set(i, 'n'); //reset checkbox tracker
                    }
                    turn++; //increase turn
                    f.repaint();
                }
                else {
                    turnOver = true;
                }

                if(turnOver == true){ //if turn over, score Hand
                    scoreTurn();

                }
                
            }
        });
        //add to frame
        panel.add(playerNameLabel);
        panel.add(instructionsLabel);
        panel.add(rollDie);
        //panel.add(showScore);
        this.updateScoreCard();
        this.showScoreCard();
        f.add(panel, BorderLayout.EAST);
        //f.pack();
        f.setVisible(true);
    }

    /**
     * This method rolls each die in the dieImages array. Created specifically for use after scoring and before creating a new hand.
     *
     * No params
     * No returns
     */
    public void rollNewDie(){
        //set keepDieArray to all 'y' so all die are rolled
        for(int i = 0; i < dieImages.size(); i++){
            keepDieArray.set(i, 'y');
        }
        //get rolled die
        ArrayList<Die> newDieList = game.playTurn(keepDieArray, turn);

        if (turn < 3 && !(game.getToKeepGoal().equals(keepDieArray))) {
            for (int i = 0; i < dieImages.size(); i++) {
                dieImages.get(i).setIcon(newDieList.get(i).getValue()); //update dice
                keepDieArray.set(i, 'n');
            }
            //print to console
            f.repaint();
        }
    }

    /**
     * This method outputs buttons representing the possible scores of the players hand to the GUI.
     *
     * No params
     * No returns
     */
    public void scoreTurn(){
        //show player's sorted hand
        printSortedDice();

        //GUI Layout Setup
        JPanel mainPanel = new JPanel();
        JPanel heading = new JPanel();
        JPanel scores = new JPanel();
        JButton nextTurnButton = new JButton("Next Player's Turn\n-->");
        nextTurnButton.setFont(new Font("Arial", Font.PLAIN, 25));
        scores.setLayout(new BoxLayout(scores, BoxLayout.Y_AXIS));
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JLabel header = new JLabel("Above is your sorted hand of dice.\n");
        JLabel header3 = new JLabel("Click on the score you would like to add to your scorecard:          ");
        heading.add(header);
        heading.add(header3);

        //setup possible score card buttons
        ArrayList<PossibleScoreButton> possScoreButtons = game.scoreHand();
        for(int i = 0; i < possScoreButtons.size(); i++){
            int val = i;
            possScoreButtons.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    game.getPlayer().updateScore(possScoreButtons.get(val).getScoreCode(), possScoreButtons.get(val).getScoreNum());
                    updateScoreCard();
                    // testing
                    mainPanel.removeAll();
                    mainPanel.repaint();
                    mainPanel.add(new JLabel("Score has been added to your scorecard!"));
                    f.add(nextTurnButton, BorderLayout.EAST);
                    f.setVisible(true);
                }
            });

            //set the test and add to panel
            possScoreButtons.get(i).setText(possScoreButtons.get(i).returnButtonString());
            scores.add(possScoreButtons.get(i));
        }

        //Action listener for next player's turn button
        nextTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(game.gameOver() == true){
                    f.getContentPane().removeAll();
                    f.repaint();
                    //reset turn variables
                    turn = 1;
                    turnOver = false;
                    game.updateCurrPlayer();
                    // changes scorecard to reflect current players card
                    updateScoreCard();
                    //showScoreCard();
                    playDiceTurn();
                }
                else{
                    //show closing remarks and final score
                    closingRemarks();
                }
            }
        });
        //add to frame
        mainPanel.add(heading);
        mainPanel.add(scores);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        f.add(mainPanel, BorderLayout.CENTER);
        //show current scorecard in west panel
        if(game.gameOver() == true) {
            this.updateScoreCard();
            this.showScoreCard();
        }
        f.setVisible(true);
    }

    /**
     * This method writes the closing remarks to the screen
     *
     */
    public void closingRemarks(){
        f.getContentPane().removeAll();
        f.repaint();
        String winner = game.getWinnerName();
        int winningScore = game.getWinningScore();
        

        //closing remarks
        JLabel closingRemarks = new JLabel("Thank you for playing! " + winner + " is the winner with a score of " + winningScore + "!");
        closingRemarks.setFont(new Font("Arial", Font.BOLD, 20));
        f.add(closingRemarks, BorderLayout.CENTER);
        f.setVisible(true);
        // go through and show each scorecard
        JPanel allScoresPanel = new JPanel(new FlowLayout());
        for (Player p : game.getGamePlayers()) {
            game.updateCurrPlayer();
            updateScoreCard();
            //create panel
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            //panel for player name
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            JLabel scorecardTitle = new JLabel("SCORECARD: " + p.getPlayerName());
            scorecardTitle.setFont(scorecardTitle.getFont().deriveFont(18.0f));
            panel.add(scorecardTitle);

            //panel for scorecard header
            JPanel header = new JPanel();
            JLabel label = new JLabel("Line             Score");
            panel.add(label);

            //calculate totals and bonus:
            int upperTotal = p.getScore().getUpperTotal();
            int bonus = 0;
            if (Integer.valueOf(upperTotal) >= 63) {
                bonus = 35;
            }
            int lowerTotal = p.getScore().getLowerScore();
            int grandTotal = upperTotal + lowerTotal + bonus;

            //add each score to a JLabel
            for (int i = 0; i < scoreLabels.size(); i++) {
                if (scoreLabels.get(i).returnScoreCode() == "3 of a Kind") { //add totals before lower section
                    panel.add(new JLabel("----------------"));
                    panel.add(new JLabel("Sub Total:            " + upperTotal));
                    panel.add(new JLabel("Bonus                  " + bonus));
                    panel.add(new JLabel("----------------"));
                    panel.add(new JLabel("Upper Total         " + (upperTotal + bonus)));
                    panel.add(scoreLabels.get(i));
                } else if (scoreLabels.get(i).returnScoreCode() == "Chance") { //add totals after lower section
                    panel.add(scoreLabels.get(i));
                    panel.add(new JLabel("----------------"));
                    panel.add(new JLabel("Lower total           " + lowerTotal));
                    panel.add(new JLabel("----------------"));
                    panel.add(new JLabel("GRAND TOTAL          " + grandTotal));
                } else {
                    panel.add(scoreLabels.get(i));
                }
            }
            mainPanel.add(header);
            mainPanel.add(panel);
            mainPanel.setBorder(new EmptyBorder(6, 6, 6, 6));
            allScoresPanel.add(mainPanel);
            f.setVisible(true);
        }
        f.add(allScoresPanel, BorderLayout.WEST);
        f.setVisible(true);
        //HERE
    }

    /**
     * This method outputs to the frame the player's dice hand in sorted order.
     *
     * Side effects: the dieImages order and images are changed.
     *
     * No params
     * No returns
     */
    private void printSortedDice(){
        //clear frame
        f.getContentPane().removeAll();
        //create new panel
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBorder(new EmptyBorder(10,10,10,10));

        ArrayList<Die> sortedList = game.getSortedGameHand(); //sorted list from the final turn
        for (int i = 0; i < dieImages.size(); i++) {
            dieImages.get(i).setIcon(sortedList.get(i).getValue()); //change dieImages at i to the sorted value at i
            panel.add(dieImages.get(i));
        }
        //add to panel
        f.add(panel, BorderLayout.NORTH);
        f.repaint();
        f.setVisible(true);
    }

    /**
     * This method creates the dieImages and checkBoxes before adding them to the Frame for user use. In addition to this, it creates
     * a keepDieArray to keep track of which checkBoxes are clicked.
     *
     * No params
     * No returns
     */
    public void createDieImages(){
        dieImages.clear(); //clear die array
        checkBoxed.clear(); //clear checkboxes
        keepDieArray.clear(); //clear checkbox tracking array

        //panel setup
        JPanel mainPanel = new JPanel();
        JPanel panel = new JPanel();
        JPanel checkBoxPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); //Box layout to stack die and checkboxes

        for(int i = 0; i < game.getGameHand().returnNumDice(); i++) {
            String text = "Dice " + (i + 1);
            keepDieArray.add('n'); //add all 'n' to array
            dieImages.add(new DieLabel(game.getGameHand().getHand().get(i))); //add Die Labels to array
            checkBoxed.add(new JCheckBox(text)); //add checkboxes

            panel.add(dieImages.get(i)); // add die images to frame
        }

        for(int i = 0; i < checkBoxed.size(); i++){
            checkBoxed.get(i).addItemListener(this); //create action listener
            checkBoxPanel.add(checkBoxed.get(i)); //add to frame
        }

        //add to panel
        mainPanel.add(panel);
        mainPanel.add(checkBoxPanel);
        f.add(mainPanel, BorderLayout.NORTH);
    }

    /**
     * This method overrides the itemStateChanged method and updates the keepDieArray when a checkBox
     * is clicked
     *
     * @param e ItemEvent: the click that happened on the GUI
     */
    @Override
    public void itemStateChanged(ItemEvent e){
        //go through each checkbox and check if it was the one clicked
        for(int i = 0; i < checkBoxed.size(); i++){
            if(e.getSource() == checkBoxed.get(i)){
                if(keepDieArray.get(i).equals('y')) {
                    keepDieArray.set(i, 'n'); //if checked change to 'n'
                }
                else{
                    keepDieArray.set(i, 'y'); //if checked change to 'y
                }
            }
        }
    }
}