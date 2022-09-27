package edu.gonzaga;

import javax.swing.*;
import java.awt.*;

/**
 * This class keeps track of the ScoreCardLabels
 */
public class ScoreCardLabel extends JLabel {
    /**
     * scoreNum keeps track of the score for the label
     */
    private String scoreNum;
    /**
     * scoreCord holds the string code of the score line
     */
    private String scoreCode;
    /**
     * String text hold the text that the JLabel should display
     */
    private String text;
    /**
     * scoreLabel is the actual label object of the class
     */
    private JLabel scoreLabel = new JLabel();

    /**
     * This constructor sets the text for the JLabel using the num and code provided
     *
     * @param num The num that the score line has
     * @param code The code for the score line
     */
    public ScoreCardLabel(String num, String code){
        scoreNum = num;
        scoreCode = code;
        if(scoreCode.equals("Yahtzee") || scoreCode.equals("Chance")) {
            text = scoreCode + "                  " + scoreNum;
        }
        else if(scoreCode.equals("3 of a Kind") || scoreCode.equals("4 of a Kind") || scoreCode.equals("Full House")){
            text = scoreCode + "             " + scoreNum;
        }
        else if(scoreCode.equals("Small Straight") || scoreCode.equals("Large Straight")){
            text = scoreCode + "         " + scoreNum;
        }
        else{
            text = scoreCode + "                          " + scoreNum;
        }
        //scoreLabel.setBounds(0,100,100,30);
        scoreLabel.setText("Running a test");
    }


    /**
     * This method returns the score code of the class
     *
     * @return scoreCode String code of the score line
     */
    public String returnScoreCode(){
        return scoreCode;
    }


    /**
     * This method returns the text for the ScoreCardLabel
     *
     * @return text the String of the label
     */
    public String getText(){
        return text;
    }

    /**
     * This method updates the text of the JLabel with the new score provided in the parameter
     *
     * @param newScore the new score for the score line
     */
    public void updateScore(String newScore){
        scoreNum = newScore;
        if(scoreCode.equals("Yahtzee") || scoreCode.equals("Chance")) {
            text = scoreCode + "                  " + scoreNum;
        }
        else if(scoreCode.equals("3 of a Kind") || scoreCode.equals("4 of a Kind") || scoreCode.equals("Full House")){
            text = scoreCode + "             " + scoreNum;
        }
        else if(scoreCode.equals("Small Straight") || scoreCode.equals("Large Straight")){
            text = scoreCode + "         " + scoreNum;
        }
        else{
            text = scoreCode + "                          " + scoreNum;
        }
    }
}

