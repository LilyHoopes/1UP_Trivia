package Model;

/**
 * Represents the state of a door in the maze.
 * OPEN: The door has been answered correctly and can be passed through.
 * CLOSED: The door hasn't been attempted yet.
 * LOCKED: The door was attempted but the answer was wrong.
 */
public enum DoorState {
    OPEN, CLOSED, LOCKED;
}

