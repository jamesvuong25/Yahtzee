package edu.gonzaga;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;



public class GeneralTest{
    // Game Tests
    @Test
    void testGameConstructor() {
        Player[] players = new Player[4];
        ArrayList<String> playerNames = new ArrayList<>();
        playerNames.add("Hunter");
        playerNames.add("Connor");
        playerNames.add("James");
        playerNames.add("Jared");
        Game game = new Game(players, playerNames);
        assertEquals(4, game.getGamePlayers().length);
        assertEquals("Hunter", game.getGamePlayers()[0].getPlayerName());
        assertEquals("Connor", game.getGamePlayers()[1].getPlayerName());
        assertEquals("James", game.getGamePlayers()[2].getPlayerName());
        assertEquals("Jared", game.getGamePlayers()[3].getPlayerName());
    }

    // Player Tests
    @Test
    void testPlayerConstructor() {
        Player player = new Player("TestPlayer");
        assertEquals("TestPlayer", player.getPlayerName());
    }

    @Test
    void testPlayerLowerScoreCard() {
        Player player = new Player("TestPlayer");
        ScoreCard scoreCard = player.getScore();
        assertEquals(0, scoreCard.getLowerScore());
    }

    @Test
    void testPlayerUpperScoreCard() {
        Player player = new Player("TestPlayer");
        ScoreCard scoreCard = player.getScore();
        assertEquals(0, scoreCard.getUpperTotal());
    }

    // Check Die Lock - Reroll
    // Rolls the dice up to 5 times and returns true if the dice value changes
    // To test this however, multiple other objects in the game need to be performed also.
    // This is system/integrating testing
    @Test
    void testCheckDieLock() {
        boolean diffVal = false;
        int rollCount = 10;
        ArrayList<String> playerNamesList = new ArrayList<String>();
        playerNamesList.add("TestPlayer");
        Game game = new Game(new Player[1], playerNamesList);
        ArrayList<Character> keepDieArray = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            // Reroll is set to y/true. This will cause the die to roll again
            keepDieArray.add('y');
        }
        ArrayList<Die> newDieList = game.playTurn(keepDieArray, 1);
        Die One = newDieList.get(0);
        int initValue = One.getValue();
        for (int i = 0; i < rollCount; i++) {
            newDieList = game.playTurn(keepDieArray, 1);
            One = newDieList.get(0);
            if (One.getValue() != initValue) {
                diffVal = true;
            }
        }
        assertTrue(diffVal);
    }

    // "Locks" the dice then rerolls to ensure the value stays the same.
    // Rolls the dice 5 times and returns true if the dice value stays the same
    // To test this however, multiple other objects in the game need to be performed also.
    // This is system/integrating testing
    @Test 
    void checkDieKeep() {
        boolean sameVal = true;
        int rollCount = 5;
        ArrayList<String> playerNamesList = new ArrayList<String>();
        playerNamesList.add("Hunter");
        Game game = new Game(new Player[1], playerNamesList);
        ArrayList<Character> keepDieArray = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            // Reroll is set to n/false. This will cause the die to not roll again
            keepDieArray.add('n');
        }
        ArrayList<Die> newDieList = game.playTurn(keepDieArray, 1);
        Die One = newDieList.get(0);
        int initValue = One.getValue();
        for (int i = 0; i < rollCount; i++) {
            newDieList = game.playTurn(keepDieArray, 1);
            One = newDieList.get(0);
            if (One.getValue() != initValue) {
                sameVal = false;
            }
        }
        assertTrue(sameVal);
    }

    // ScoreCard Tests
    @Test
    void testScoreCardConstructor() {
        ScoreCard scoreCard = new ScoreCard("Test");
        assertEquals(0, scoreCard.getLowerScore());
        assertEquals(0, scoreCard.getUpperTotal());
    }
    
}
