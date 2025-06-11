package Test;

import Model.TriviaQuestion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the TriviaQuestion class.
 * Tests correctness checking, getters, and toString method.
 *
 * @author Lily Hoopes
 * @author Komalpreet Dhaliwal
 * @author Christiannel Maningat
 * @version 6/10/2025
 */
class TriviaQuestionTest {

    /**
     * Tests the isCorrect method for various inputs,
     * including case insensitivity and trimming whitespace.
     */
    @Test
    void isCorrect() {
        String[] choices = {"Fire Flower", "Super Star", "Mushroom", "1-Up"};
        TriviaQuestion tq = new TriviaQuestion("Which item grants Mario invincibility?", choices, "Super Star");

        assertTrue(tq.isCorrect("Super Star"));
        assertTrue(tq.isCorrect("super star"));       // case insensitive
        assertTrue(tq.isCorrect("  super star  "));   // trims whitespace
        assertFalse(tq.isCorrect("Fire Flower"));
        assertFalse(tq.isCorrect(""));
    }

    /**
     * Tests that getQuestionText returns the expected question string.
     */
    @Test
    void getQuestionText() {
        String[] choices = {"Peach", "Daisy", "Rosalina", "Toadette"};
        TriviaQuestion tq = new TriviaQuestion("Who is the princess of the Mushroom Kingdom?", choices, "Peach");
        assertEquals("Who is the princess of the Mushroom Kingdom?", tq.getQuestionText());
    }

    /**
     * Tests that getMultipleChoices returns the correct array of choices.
     */
    @Test
    void getMultipleChoices() {
        String[] choices = {"Goomba", "Koopa Troopa", "Shy Guy", "Bob-omb"};
        TriviaQuestion tq = new TriviaQuestion("Which of the following is not an enemy?", choices, "None");
        assertArrayEquals(choices, tq.getMultipleChoices());
    }

    /**
     * Tests that getCorrectAnswer returns the expected correct answer string.
     */
    @Test
    void getCorrectAnswer() {
        String[] choices = {"Red Shell", "Green Shell", "Blue Shell", "Banana"};
        TriviaQuestion tq = new TriviaQuestion("Which item targets the player in first place?", choices, "Blue Shell");
        assertEquals("Blue Shell", tq.getCorrectAnswer());
    }

    /**
     * Tests the toString method formats question and answer properly.
     */
    @Test
    void testToString() {
        String[] choices = {"Mario", "Luigi", "Toad", "Yoshi"};
        TriviaQuestion tq = new TriviaQuestion("Who is the main character of the series?", choices, "Mario");
        String expected = "Question: Who is the main character of the series? | Answer: Mario";
        assertEquals(expected, tq.toString());
    }
}
