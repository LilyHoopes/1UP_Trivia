/*
 * This class represents a single trivia question with multiple-choice options and
 * the correct answer. It provides methods for checking correctness and accessing
 * question details.
 */

package Model;

import java.io.Serializable;

/**
 * Represents a trivia question with four multiple-choice answers and a correct answer.
 * Tracks whether the question has been answered correctly.
 *
 * Used in the trivia maze game to determine if a player may pass through a door.
 *
 * @author Lily Hoopes
 * @author Komalpreet Dhaliwal
 * @author Christiannel Maningat
 * @version 5/20/2025
 */
public class TriviaQuestion implements Serializable {
    private static final long serialVersionUID = 1L;

    /** The text of the trivia question. */
    private final String myQuestionText;

    /** The multiple-choice options for this question. */
    private final String[] myMultipleChoices;

    /** The correct answer to the question. */
    private final String myCorrectAnswer;

    /**
     * Constructs a trivia question.
     *
     * @param theQuestionText the question text.
     * @param theChoices the multiple-choice options.
     * @param theCorrectAnswer the correct answer.
     */
    public TriviaQuestion(final String theQuestionText, final String[] theChoices,
                          final String theCorrectAnswer) {
        this.myQuestionText = theQuestionText;
        this.myMultipleChoices = theChoices;
        this.myCorrectAnswer = theCorrectAnswer;
    }

    /**
     * Checks whether the given answer is correct.
     *
     * @param theUserAnswer the answer to check.
     * @return true if the answer is correct, false otherwise.
     */
    public boolean isCorrect(final String theUserAnswer) {
        //System.out.println("actual answer from isCorrect method:" + myCorrectAnswer);
        //System.out.println("theUserAnswer from isCorrect method:" + theUserAnswer);
        boolean isCorrect = theUserAnswer.trim().equalsIgnoreCase(myCorrectAnswer);
        //System.out.println("boolean isCorrect from isCorrect method:" + isCorrect);

        return isCorrect;
    }

    /**
     * Returns the question text.
     *
     * @return the question text.
     */
    public String getQuestionText() {
        return myQuestionText;
    }

    /**
     * Returns the multiple-choice options.
     *
     * @return an array of answer choices.
     */
    public String[] getMultipleChoices() {
        return myMultipleChoices;
    }

    /**
     * Returns the correct answer.
     *
     * @return the correct answer
     */
    public String getCorrectAnswer() {
        return myCorrectAnswer;
    }

    /**
     * Returns a string representation of the question (for debugging or testing).
     *
     * @return formatted question and answer string.
     */
    @Override
    public String toString() {
        // FOR TESTING
        return "Question: " + myQuestionText + " | Answer: " + myCorrectAnswer;
    }
}