/*
 * This is an enum for the four directions used in maze movement.
 * Each direction has a row and column offset and supports retrieving
 * its opposite (e.g., UP <-> DOWN).
 *
 * Directions:
 *   UP    – moves the player north (row - 1, col + 0)
 *   RIGHT – moves the player east  (row + 0, col + 1)
 *   DOWN  – moves the player south (row + 1, col + 0)
 *   LEFT  – moves the player west  (row + 0, col - 1)
 */

package Model;

/**
 * Enum representing the four directions used for movement
 * within the maze: UP (North), RIGHT (East), DOWN (South), and LEFT (West).
 * Each direction has a corresponding row and column offset.
 *
 * The offsets are used to calculate movement in a 2D grid.
 * For example, UP has a row offset of -1, which moves the player upward in the grid.
 *
 * This enum also provides a method to get the opposite direction.
 *
 * @author Lily Hoopes
 * @author Komalpreet Dhaliwal
 * @author Christiannel Maningat
 * @version 5/22/2025
 */

public enum Direction {
    /** Moves up (North): row decreases by 1. */
    UP(-1, 0), //-1 moves us up 1, which is North

    /** Moves right (East): column increases by 1. */
    RIGHT(0, 1), //1 moves us right 1, which is East

    /** Moves down (South): row increases by 1. */
    DOWN(1, 0), //1 moves us down 1, which is South

    /** Moves left (West): column decreases by 1. */
    LEFT(0, -1); //-1 moves us left 1, which is West

    /** The change in row associated with the direction. */
    private final int myRowOffset;


    /** The change in column associated with the direction. */
    private final int myColOffset;


    /**
     * Constructs a direction with the given row and column offsets.
     *
     * @param theRowOffset the change in row.
     * @param theColOffset the change in column.
     */
    Direction(final int theRowOffset, final int theColOffset) {
        myRowOffset = theRowOffset;
        myColOffset = theColOffset;
    }

    /**
     * Returns the row offset for this direction.
     *
     * @return the row offset.
     */
    public int getRowOffset() {
        return myRowOffset;
    }

    /**
     * Returns the column offset for this direction.
     *
     * @return the column offset
     */
    public int getColOffset() {
        return myColOffset;
    }

    /**
     * Returns the opposite direction.
     *
     * @return the opposite Direction.
     */
    public Direction opposite() {
        switch (this) {
            case UP: return DOWN;
            case DOWN: return UP;
            case RIGHT:  return LEFT;
            case LEFT:  return RIGHT;
            default: throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}
