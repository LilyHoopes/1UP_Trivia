/*
 * Represents the player in the maze, tracking their location and handling movement logic.
 * The player starts in the top left corner of the maze and moves based on door availability.
 * Movement is only possible through open doors, and win/loss conditions are determined
 * by reaching the exit or having all directions blocked.
 *
 * Serializable for saving and loading game state.
 */

package Model;

import java.awt.*;
import java.io.Serializable;

/**
 * Represents the player in the maze, tracking their location
 * and handling movement logic.
 *
 * @author Lily Hoopes
 * @author Komalpreet Dhaliwal
 * @author Christiannel Maningat
 * @version 5/25/2025
 */
public class Player implements Serializable {

    /** The player's current row in the maze. */
    private int myPlayerRow;

    /** The player's current column in the maze. */
    private int myPlayerColumn;

    /** The maze in which the player exists. */
    private final Maze myMaze;

    /**
     * Creates a new player starting at position (0, 0) in the given maze.
     *
     * @param theMaze the maze the player is in.
     */
    public Player(final Maze theMaze) {
        myMaze = theMaze;
        myPlayerRow = 0;
        myPlayerColumn = 0;
    }

    /**
     * Returns the current row the player is in.
     *
     * @return current row index.
     */
    public int getRow() {
        return myPlayerRow;
    }

    /**
     * Returns the current column the player is in.
     *
     * @return current column index.
     */
    public int getCol() {
        return myPlayerColumn;
    }

    /**
     * Returns the room the player is currently in.
     *
     * @return current room.
     */
    public Room getCurrentRoom() {
        return myMaze.getCurrentRoom();
    }

    /**
     * Will move the player in the given direction without answering a question.
     * This will only succeed if the door is open.
     *
     * @param theDir the direction to move.
     */
    public void moveThroughOpenDoor(final Direction theDir) {

        System.out.println("Inside moveThroughOpenDoor method");

        Room current = getCurrentRoom();
        Door door = current.getDoor(theDir);

        if (door != null && door.isOpen()) {
            int newRow = myPlayerRow + theDir.getRowOffset();
            int newCol = myPlayerColumn + theDir.getColOffset();

            if (myMaze.isInBounds(newRow, newCol)) {
                myPlayerRow = newRow;
                myPlayerColumn = newCol;
            }
        }

        System.out.println("Player location: " +  myPlayerRow + ", " + myPlayerColumn);
    }

    /**
     * Checks if the player has reached the maze exit.
     *
     * @return true if the game is won, false otherwise.
     */
    public boolean isGameWon() {
        // returns true if player reached exit
        return myPlayerRow == 3 && myPlayerColumn == 3;
    }

    /**
     * Determines if the player has lost the game.
     * A loss occurs when all possible directions are either out of bounds or locked.
     *
     * @return true if the game is lost, false otherwise.
     */
    public boolean isGameLost() {
        //returns true if all possible directions are locked or invalid
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
