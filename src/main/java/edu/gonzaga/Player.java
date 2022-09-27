package edu.gonzaga;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Yahtzee player
 */
public class Player {
    /**
     * This string keeps track of the name for the player
     */
    private String playerName;
    /**
     * This PlayerHand keeps track of the hand associated with the player
     */
    private PlayerHand hand;
    /**
     * possScores represents the class that computes and holds the possible scores for each turn
     */
    private PossibleScores possScores;
    /**
     * score represents the scoreCard object that holds the players scores
     */
    private ScoreCard score;
    /**
     * score card specific to this player
     */
    private String fileName;

    /**
     * Explicit constructor that takes in the player name and creates a player hand associated with it
     *
     * @param enteredName the name the player decided for themselves
     */
    public Player(String enteredName){
        fileName = enteredName + "_ScoreCard.txt";
        playerName = enteredName;
        hand = new PlayerHand(playerName);
        possScores = new PossibleScores();
        score = new ScoreCard(fileName);
    }

    /**
     * This method sets a new player name based on the input
     *
     * @param newName the string name the player will now be known as
     */
    public void setPlayerName(String newName){
        playerName = newName;
    }

    /**
     * This method returns the name of this particular player
     *
     * @return playerName
     */
    public String getPlayerName(){
        return playerName;
    }

    /**
     * This method returns the hand associated with the player
     *
     * @return hand the player's gameHand
     */
    public PlayerHand getPlayerHand(){
        return hand;
    }

    /**
     * This method finds the possible scores for the current hand
     *
     * @param sides number of sides on each die
     * @return an arrayList representing the possScores that were found
     */
    public ArrayList<PossibleScoreButton> possScore(int sides){

        possScores.setUpperScoreCard(hand, sides);
        //setup lower if possible
        if (hand.returnNumDice() >= 5) {
            possScores.setLowerScoreCard(hand);
        }

        //print potential scores
        return possScores.createPossButtons(hand.returnNumDice(), score);
    }

    /**
     * This method updates the scoreCard based on the scoringCode and new num that was inputted by the user
     *
     * @param scoringCode the scoreline code
     * @param scoreNum the new scoreline value
     */
    public void updateScore(String scoringCode, String scoreNum){

        //update ScoreCard
        List<ArrayList<String>> list = possScores.possiblePlaces(score);
        for (ArrayList<String> loop : list) {
            if (loop.get(0).equals(scoringCode)) {
                scoreNum = loop.get(1);
                break;
            }
        }
        score.updateScoreCard(scoringCode, scoreNum);
    }

    /**
     * This method returns the scoreCard object
     *
     * @return score the scoreCard object
     */
    public ScoreCard getScore(){
        return score;
    }

    /**
     * This value returns the PossibleScores object
     *
     * @return possScores the possibleScores object
     */
    public PossibleScores getPossScores(){
        return possScores;
    }

    public int getCurrentScore(){
        int total = score.getUpperTotal();
        total += score.getLowerScore();
        return total;
    };
}
