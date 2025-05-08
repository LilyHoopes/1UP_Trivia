package Model;

public class TriviaQuestion {
    private String myQuestionText;
    private String[] myMultipleChoices;
    private String myCorrectAnswer;
    private boolean myIsQuestionCorrect;

    public TriviaQuestion(String questionText, String[] choices, String correctAnswer) {
        this.myQuestionText = questionText;
        this.myMultipleChoices = choices;
        this.myCorrectAnswer = correctAnswer;
    }

    public boolean isCorrect(String answer) {
        return answer.equalsIgnoreCase(myCorrectAnswer);
    }
}

