package Test;

import Model.QuestionFactory;
import Model.TriviaQuestion;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the QuestionFactory class.
 * Tests loading trivia questions from a database and sequential access.
 *
 * @author Lily Hoopes
 * @author Komalpreet Dhaliwal
 * @author Christiannel Maningat
 * @version 6/10/2025
 */
class QuestionFactoryTest {

    /** Path to the test trivia question database file. */
    private static final String TEST_DB_PATH = "src/trivia_questions.db";

    /**
     * Tests that the QuestionFactory loads questions properly.
     * Verifies that questions are loaded, and getters return expected data.
     */
    @Test
    void testFactoryLoadsQuestions() {
        QuestionFactory factory = new QuestionFactory(TEST_DB_PATH);

        // Factory should load at least one question
        assertTrue(factory.size() > 0, "Factory should load some questions");

        // Static getter returns questions too
        ArrayList<TriviaQuestion> questions = QuestionFactory.getQuestions();
        assertNotNull(questions);
        assertEquals(factory.size(), questions.size());

        for (TriviaQuestion q : questions) {
            assertNotNull(q, "Question object should not be null");
            assertNotNull(q.getQuestionText(), "Question text should not be null");
            assertNotNull(q.getCorrectAnswer(), "Correct answer should not be null");
        }
    }

    /**
     * Tests sequential access of questions using getNextQuestion().
     * Ensures all questions are returned, and null is returned after all are exhausted.
     */
    @Test
    void testGetNextQuestionSequentialAccess() {
        QuestionFactory factory = new QuestionFactory(TEST_DB_PATH);

        int count = 0;
        TriviaQuestion q;
        while ((q = factory.getNextQuestion()) != null) {
            assertNotNull(q, "getNextQuestion() should not return null prematurely");
            count++;
        }

        assertEquals(factory.size(), count, "Should return all loaded questions");

        // After all questions, next question should be null
        assertNull(factory.getNextQuestion());
    }
}
