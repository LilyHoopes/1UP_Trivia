package Test;

import Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Maze class.
 * Checks player setup, room access, bounds, and game status.
 *
 * @author Lily Hoopes
 * @author Komalpreet Dhaliwal
 * @author Christiannel Maningat
 * @version 6/10/2025
 */

class MazeTest {

    /** The maze to test. */
    private Maze maze;

    /**
     * Sets up a 4x4 maze before each test.
     */
    @BeforeEach
    void setUp() {
        maze = new Maze(4, 4);
    }

    /**
     * Checks that the player is created and starts at (0, 0).
     */
    @Test
    void getPlayer() {
        assertNotNull(maze.getPlayer(), "Player should not be null");
        assertEquals(0, maze.getPlayer().getRow(), "Player should start at row 0");
        assertEquals(0, maze.getPlayer().getCol(), "Player should start at col 0");
    }

    /**
     * Tests if coordinates are inside the maze bounds.
     */
    @Test
    void isInBounds() {
        assertTrue(maze.isInBounds(0, 0));
        assertTrue(maze.isInBounds(3, 3));
        assertFalse(maze.isInBounds(-1, 0));
        assertFalse(maze.isInBounds(4, 4));
    }

    /**
     * Checks that the current room is correct and not null.
     */
    @Test
    void getCurrentRoom() {
        Room room = maze.getCurrentRoom();
        assertNotNull(room);
        assertEquals(0, room.getRow());
        assertEquals(0, room.getCol());
    }

    /**
     * Game should not be won at the start.
     */
    @Test
    void isGameWon() {
        assertFalse(maze.isGameWon());
    }

    /**
     * Game should not be lost at the start.
     */
    @Test
    void isGameLost() {
        assertFalse(maze.isGameLost());
    }

    /**
     * Tests getting a room by its row and column.
     * Also checks for index out-of-bounds errors.
     */
    @Test
    void getRoomAt() {
        Room room = maze.getRoomAt(1, 2);
        assertNotNull(room);
        assertEquals(1, room.getRow());
        assertEquals(2, room.getCol());

        assertThrows(IndexOutOfBoundsException.class, () -> maze.getRoomAt(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> maze.getRoomAt(10, 10));
    }
}
