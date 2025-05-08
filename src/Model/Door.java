package Model;


public class Door extends Room {
    private TriviaQuestion myQuestion;
    private DoorState myState; // Enum: OPEN, CLOSED, LOCKED

    public Door(TriviaQuestion question) {
        this.myQuestion = question;
        this.myState = DoorState.CLOSED;
    }

    public boolean answerQuestion(String answer) {
        return myQuestion.isCorrect(answer);
    }
}
