package Model;

/*
Represents a single trivia question, with multiple-choice options and a correct answer.*/
public class TriviaQuestion {
    private final String myQuestionText;
    private final String[] myMultipleChoices;
    private final String myCorrectAnswer;
    private boolean myIsQuestionCorrect;

    public TriviaQuestion(String questionText, String[] choices, String correctAnswer) {
        this.myQuestionText = questionText;
        this.myMultipleChoices = choices;
        this.myCorrectAnswer = correctAnswer;
    }

    //TODO i made this method just so model could run, not sure if this is needed or should be here, which is why i have it returning nothing
    public static TriviaQuestion getRandomQuestion() {
        return null;
    }

    public boolean isCorrect(String answer) {
        boolean isCorrect = answer.equalsIgnoreCase(myCorrectAnswer);
        myIsQuestionCorrect = isCorrect;
        return isCorrect;
    }

    public String getQuestionText() {
        return myQuestionText;
    }

    public String[] getMultipleChoices() {
        return myMultipleChoices;
    }

    public String getCorrectAnswer() {
        return myCorrectAnswer;
    }

    //for testing
    @Override
    public String toString() {
        return "Question: " + myQuestionText + " | Answer: " + myCorrectAnswer;
    }

}