/*
 * This class represents a door between maze rooms that requires answering
 * a trivia question. Tracks its state (OPEN, CLOSED, LOCKED) and holds an
 * associated question.
 */

package Model;

/**
 * Represents a door between two rooms that may require answering a trivia question
 * to pass through. Each door holds a TriviaQuestion and tracks its state.
 *
 * @author Lily Hoopes
 * @author Komalpreet Dhaliwal
 * @author Christiannel Maningat
 * @version 5/17/2025
 */

public class Door {


    /** The trivia question that must be answered to open this door. */
    private TriviaQuestion myQuestion;

    /** The current state of the door (OPEN, CLOSED, LOCKED). */
    private DoorState myState;

    /**
     * Constructs a Door with a trivia question. The default state is CLOSED.
     *
     * @param theQuestion the trivia question associated with this door.
     */
    public Door(final TriviaQuestion theQuestion) {
        this.myQuestion = theQuestion;
        this.myState = DoorState.CLOSED;
    }
    //TODO: i changed the doorState from closed to open int he above code for testing, it should normally be CLOSED

    /**
     * Returns the current state of the door.
     *
     * @return the current DoorState.
     */
    public DoorState getState() {
        //returns current state of door (unlocked, locked, closed)
            return myState;
    }


    /**
     * Returns the trivia question associated with this door.
     *
     * @return the associated TriviaQuestion.
     */
    public TriviaQuestion getQuestion() {
        //gets trivia question associated with this door
        return myQuestion;
    }

    /**
     * Checks whether the door is currently open.
     *
     * @return true if the door is open, false otherwise.
     */
    public boolean isOpen() {
        //returns true if door is open
        return myState == DoorState.OPEN;
    }

    /**
     * Checks whether the door is currently locked.
     *
     * @return true if the door is locked, false otherwise.
     */
    public boolean isLocked() {
        //returns true if door is locked
        return myState == DoorState.LOCKED;
    }

    /**
     * Locks the door, called when the player answers a
     * trivia question wrong.
     */
    public void lockDoor() {
        //locks door, when player answers question wrong
        myState = DoorState.LOCKED;
    }

    /**
     * Opens the door, typically called when the player
     * answers a trivia question correctly.
     */
    public void openDoor() {
        //opens door, when player answers question right
        myState = DoorState.OPEN;
    }

}
