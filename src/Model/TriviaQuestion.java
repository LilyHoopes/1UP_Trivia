/*
 * This class represents a single trivia question with multiple-choice options and
 * the correct answer. It provides methods for checking correctness and accessing
 * question details.
 */

package Model;

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
public class TriviaQuestion {

    /** The text of the trivia question. */
    private final String myQuestionText;

    /** The multiple-choice options for this question. */
    private final String[] myMultipleChoices;

    /** The correct answer to the question. */
    private final String myCorrectAnswer;

    /** Indicates whether the question was answered correctly. */
    private boolean myIsQuestionCorrect;


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
     * TODO: Keep this?? Or no?
     * Retrieves a random question.
     *
     * @return null.
     */
    //TODO i made this method just so model could run, not sure if this is needed
    // or should be here, which is why i have it returning nothing
    public static TriviaQuestion getRandomQuestion() {
        return null;
    }

    /**
     * Checks whether the given answer is correct.
     *
     * @param theAnswer the answer to check.
     * @return true if the answer is correct, false otherwise.
     */
    public boolean isCorrect(final String theAnswer) {
        boolean isCorrect = theAnswer.equalsIgnoreCase(myCorrectAnswer);
        myIsQuestionCorrect = isCorrect;

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