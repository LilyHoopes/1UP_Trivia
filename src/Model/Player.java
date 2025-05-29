package Model;

import java.awt.*;
import java.io.Serializable;

/**
 * Represents the player in the maze, tracking their location and handling movement logic.
 */
public class Player implements Serializable {

    private int myPlayerRow;
    private int myPlayerColumn;
    private final Maze myMaze;

    /**
     * Creates a new player starting at position (0, 0) in the given maze.
     *
     * @param theMaze the maze the player is in
     */
    public Player(final Maze theMaze) {
        myMaze = theMaze;
        myPlayerRow = 0;
        myPlayerColumn = 0;
    }

    /**
     * Returns the current row the player is in.
     *
     * @return current row index
     */
    public int getRow() {
        return myPlayerRow;
    }

    /**
     * Returns the current column the player is in.
     *
     * @return current column index
     */
    public int getCol() {
        return myPlayerColumn;
    }

    /**
     * Returns the room the player is currently in.
     *
     * @return current room
     */
    public Room getCurrentRoom() {
        return myMaze.getCurrentRoom();
    }

    /**
     * Attempts to move the player in the given direction without answering a question.
     * This will only succeed if the door is open.
     *
     * @param theDir the direction to move
     * @return true if the move succeeded
     */
    public boolean move(final Direction theDir) {

        Room current = getCurrentRoom();
        Door door = current.getDoor(theDir);

        if (door != null && door.isOpen()) {
            int newRow = myPlayerRow + theDir.getRowOffset();
            int newCol = myPlayerColumn + theDir.getColOffset();

            if (myMaze.isInBounds(newRow, newCol)) {
                myPlayerRow = newRow;
                myPlayerColumn = newCol;
                return true;
            }
        }

        return false;
    }

    // returns true if player reached exit
    public boolean isGameWon() {
        return myPlayerRow == 3 && myPlayerColumn == 3;
    }

    //returns true if all possible directions are locked or invalid
    public boolean isGameLost() {
        Room current = getCurrentRoom();

        for (Direction dir : Direction.values()) {
            int newRow = myPlayerRow + dir.getRowOffset();
            int newCol = myPlayerColumn + dir.getColOffset();

            // Only check valid directions
            if (myMaze.isInBounds(newRow, newCol)) {
                Door door = current.getDoor(dir);
                if (door != null && !door.isLocked()) {
                    return false; // At least one move is still possible
                }
            }
        }
        // All possible directions are either locked or invalid
        return true;
    }
}
