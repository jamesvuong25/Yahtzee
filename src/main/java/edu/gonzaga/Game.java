package edu.gonzaga;

import java.util.ArrayList;

/**
 * This class takes care of the game functions
 */
public class Game {

    /**
     * gamePlayers is the array that holds all of the game players
     */
    private Player[] gamePlayers;

    /**
     * currPlayer keeps track of the index of the current player
     */
    private int currPlayer = 0;

    /**
     * toKeepGoal array that represent the goal to keep all die
     */
    ArrayList<Character> toKeepGoal = new ArrayList<>();

    /**
     * EVC that creates the players
     *
     * @param newPlayers
     */
    public Game(Player[] newPlayers, ArrayList<String> playerNames) {
        setGamePlayers(newPlayers);
        for (int i = 0; i < gamePlayers.length; i++) {
            this.gamePlayers[i] = new Player(playerNames.get(i));
            this.gamePlayers[i].getScore().setSides(6);
            this.gamePlayers[i].getScore().createScoreCard();
        }

        // Unit test for playerNames
        // Ensures that names get assigned to players correctly
        for (int i = 0; i < gamePlayers.length; i++) {
            System.out.println(gamePlayers[i].getPlayerName());
        }
    }

    /**
     * Set the number of players
     *
     * @param newPlayers the players in the game
     */
    public void setGamePlayers(Player[] newPlayers) {
        this.gamePlayers = newPlayers;
    }

    /**
     * This method returns the list of game players
     *
     * @return list of players
     */
    public Player[] getGamePlayers() {
        return gamePlayers;
    }

    /**
     * update the current player
     *
     * @param currPlayer int of new index
     */
    public void setCurrPlayer(int currPlayer) {
        this.currPlayer = currPlayer;
    }

    /**
     * This method plays a single turn of a player
     *
     * @param toKeep the arraylist of die to keep
     * @param turnNum which turn it is for the player
     * @return an arrayList of the new Die
     */
    public ArrayList<Die> playTurn(ArrayList<Character> toKeep, int turnNum){
        //read in scorecard
        gamePlayers[currPlayer].getScore().readScoreCard();
        //clear cards in case player is playing again
        gamePlayers[currPlayer].getPossScores().clear();

        //begin turn
        toKeepGoal.clear();
        for(int i = 0; i < gamePlayers[currPlayer].getPlayerHand().returnNumDice(); i++){
            toKeepGoal.add('n');
        }

        // run 3 turns or until User wants to keep all of their dice
        if(turnNum <= 3 && !(toKeep.equals(toKeepGoal))) {
            for (int i = 0; i < gamePlayers[currPlayer].getPlayerHand().returnNumDice(); i++) {
                if (toKeep.get(i).equals('y')) {
                    gamePlayers[currPlayer].getPlayerHand().rollDie(i);
                }
            }
        }

        return gamePlayers[currPlayer].getPlayerHand().getHand();
    }

    /**
     * Shows player their current scoring options and has them choose a score for this hand of dice
     * Side effects: changes the score card and the score array to show used scores and current scores
     *
     * No params
     * @return buttonArray the array of buttons to represent the possible scores
     */
    public ArrayList<PossibleScoreButton> scoreHand() {
        //update scorecard
        return gamePlayers[currPlayer].possScore(6);
    }

    /**
     * Checks if there are still scoring lines open for the user to choose
     *
     * @return true or false based on if there are lines still open
     */
    public boolean gameOver(){
        //check if more lines are available
        int numOpen = 0;
        for(int i = 0; i < gamePlayers.length; i++){
            numOpen += gamePlayers[i].getScore().scoreCardOpen();
        }
        //int numOpen= gamePlayers[currPlayer].getScore().scoreCardOpen();
        if(numOpen > 0) {
            return true;
        }
        return false;

    }

    /**
     * This method returns the gameHand being used in the game
     *
     * @return playerHand that represents the game
     */
    public PlayerHand getGameHand() {
        return gamePlayers[currPlayer].getPlayerHand();
    }

    /**
     * This method returns the sorted hand of die
     *
     * @return gameHand.getHand() after the hand has been sorted
     */
    public ArrayList<Die> getSortedGameHand(){
        gamePlayers[currPlayer].getPlayerHand().sortHand();
        return gamePlayers[currPlayer].getPlayerHand().getHand();
    }

    /**
     * This method returns the toKeepGoal
     *
     * @return toKeepGoal an arraylist of characters
     */
    public ArrayList<Character> getToKeepGoal() {
        return toKeepGoal;
    }

    /**
     * this method returns player
     *
     * @return current player
     */
    public Player getPlayer(){
        return gamePlayers[currPlayer];
    }

    /**
     * Return the current player name
     *
     * @return String of the player
     */
    public String getPlayerName(){
        return gamePlayers[currPlayer].getPlayerName();
    }

    /**
     * Update the current player to move to the next player
     *
     */
    public void updateCurrPlayer(){
        if (currPlayer < (gamePlayers.length - 1) && gamePlayers.length != 1) { // if not at length
            this.setCurrPlayer(currPlayer + 1);
        }
        // currPlayer index doesn't change if array is only one player
        else if (currPlayer == gamePlayers.length && gamePlayers.length != 1){
            this.setCurrPlayer(0);
        }
        // if at the end of the array resets to start of array
        else {
            this.setCurrPlayer(0);
        }
    }

    /**
     * This method returns the name of the winning player
     *
     * @return String name
     */
    public String getWinnerName(){
        Player winner = scoreWinner();
        return winner.getPlayerName();
    }

    /**
     * This method gets the name of the player that lost
     *
     * @return
     */
    public String getLoserName(){
        Player loser = scoreLoser();
        return loser.getPlayerName();
    }

    /**
     * This method returns the player that won the game
     *
     * @return Player that won
     */
    private Player scoreWinner(){
        ArrayList <Integer> scores = new ArrayList<>();
        for(int i = 0; i < gamePlayers.length; i++){
            scores.add(i, gamePlayers[i].getCurrentScore());
        }
        Player maxPlayer = gamePlayers[0];
        int maxScore = scores.get(0);
        for(int i = 1; i < scores.size(); i++){
            if(scores.get(i) > maxScore){ //if score is greater than the last score
                maxPlayer = gamePlayers[i]; // update player with top score
                maxScore = scores.get(i);
            }
        }
        return maxPlayer;
    }

    /**
     * Gets the score of the player who lost
     *
     * @return
     */
    private Player scoreLoser(){
        ArrayList <Integer> scores = new ArrayList<>();
        for(int i = 0; i < gamePlayers.length; i++){
            scores.add(i, gamePlayers[i].getCurrentScore());
        }
        Player minPlayer = gamePlayers[0];
        int minScore = scores.get(0);
        for(int i = 1; i < scores.size(); i++){
            if(scores.get(i) < minScore){
                minPlayer = gamePlayers[i];
                minScore = scores.get(i);
            }
        }
        return minPlayer;
    }

    /**
     * Return the score of the winning player
     *
     * @return int winning player
     */
    public int getWinningScore(){
        Player winner = scoreWinner();
        return winner.getCurrentScore();
    }

    /**
     * Return the number of players in the game
     *
     * @return int number of players
     */
    public int getNumPlayers(){
        return gamePlayers.length - 1;
    }
}
