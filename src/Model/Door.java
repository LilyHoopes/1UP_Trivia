package Model;

/**
 * Represents a door between two rooms that may require answering a trivia question
 * to pass through. Each door holds a TriviaQuestion and tracks its state.
 */
public class Door {

    private TriviaQuestion myQuestion; //the question that must be answered correctly to open door
    private DoorState myState; // Enum: OPEN, CLOSED, LOCKED

    /**
     * Constructs a Door with a trivia question. The default state is CLOSED.
     *
     * @param theQuestion the trivia question associated with this door
     */
    public Door(final TriviaQuestion theQuestion) {
        this.myQuestion = theQuestion;
        this.myState = DoorState.OPEN;
    }
    //TODO i changed the doorstate from clsoed to open int he above code for testing, it should normally be CLOSED

    //returns current state of door (unlocked, locked, closed)
    public DoorState getState() {
        return myState;
    }

    //gets trivia question associated with this door
    public TriviaQuestion getQuestion() {
        return myQuestion;
    }

    //returns true if door is open
    public boolean isOpen() {
        return myState == DoorState.OPEN;
    }

    //returns true if door is locked
    public boolean isLocked() {
        return myState == DoorState.LOCKED;
    }

    //locks door, when player answers question wrong
    public void lockDoor() {
        myState = DoorState.LOCKED;
    }

    //opens door, when player answers question right
    public void openDoor() {
        myState = DoorState.OPEN;
    }

}
