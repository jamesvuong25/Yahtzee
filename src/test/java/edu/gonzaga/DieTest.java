package edu.gonzaga;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class DieTest {

    @Test
    void testGreaterThan() {
        Die die = new Die();
        assertTrue(die.getValue() > 0 );
    }

    @Test
    void testLessThan() {
        Die die = new Die();
        assertTrue(die.getValue() < 7);
    }

    @Test
    void testRoll() {
        Die die = new Die();
        die.rollDie();
        assertTrue(die.getValue() > 0);
        assertTrue(die.getValue() < 7);
    }

}

