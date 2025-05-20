package Model;

/*
 * Represents a single trivia question, with multiple-choice options and a correct answer.
 */
public class TriviaQuestion {
    // The text of the question itself
    private final String myQuestionText;
    // Array holding the four multiple-choice options
    private final String[] myMultipleChoices;
    // The correct answer string (should match one of the options)
    private final String myCorrectAnswer;
    // Tracks if the last checked answer was correct (for UI feedback)
    private boolean myIsQuestionCorrect;

    public TriviaQuestion(String questionText, String[] choices, String correctAnswer) {
        this.myQuestionText = questionText;
        this.myMultipleChoices = choices;
        this.myCorrectAnswer = correctAnswer;
    }

    /*
     * Checks if a provided answer matches the correct answer (case-insensitive).
     * Also updates the internal flag about correctness.
     *
     * param answer The answer to check
     * return true if the answer matches the correct answer, false otherwise
     */
    public boolean isCorrect(String answer) {
        boolean isCorrect = answer.equalsIgnoreCase(myCorrectAnswer);
        myIsQuestionCorrect = isCorrect;
        return isCorrect;
    }

    /*
     * Gets the question text.
     *
     * return Question string
     */
    public String getQuestionText() {
        return myQuestionText;
    }

    /*
     * Returns the multiple-choice options.
     *
     * return String array with answer choices
     */
    public String[] getMultipleChoices() {
        return myMultipleChoices;
    }

    /*
     * Returns the correct answer string.
     *
     * return Correct answer
     */
    public String getCorrectAnswer() {
        return myCorrectAnswer;
    }

    /*
     * Returns whether the last checked answer was correct.
     *
     * return true if last answer was correct, false otherwise
     */
    public boolean wasLastAnswerCorrect() {
        return myIsQuestionCorrect;
    }
}
