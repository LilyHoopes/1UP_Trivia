package Test;

import Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Player class in the context of the Maze game.
 * Tests player location and movement within the maze.
 *
 * @author Lily Hoopes
 * @author Komalpreet Dhaliwal
 * @author Christiannel Maningat
 * @version 6/10/2025
 */

class PlayerTest {

    /** The maze used for testing. */
    private Maze maze;

    /** The player being tested. */
    private Player player;

    /**
     * Initializes a 4x4 maze and retrieves the player before each test.
     */
    @BeforeEach
    void setUp() {
        maze = new Maze(4, 4);      // 4x4 Maze
        player = maze.getPlayer();  // Player starts at (0, 0)
    }

    /**
     * Tests that the player initially starts at row 0.
     */
    @Test
    void getRow() {
        assertEquals(0, player.getRow(), "Initial row should be 0");
    }

    /**
     * Tests that the player initially starts at column 0.
     */
    @Test
    void getCol() {
        assertEquals(0, player.getCol(), "Initial column should be 0");
    }

    /**
     * Tests that the player starts in the top-left room of the maze.
     */
    @Test
    void getCurrentRoom() {
        Room expected = maze.getRoomAt(0, 0);
        assertEquals(expected, player.getCurrentRoom(), "Player should be in the top-left room");
    }

    /**
     * Tests movement through an open door to the right.
     * If the door is not open, ensures the player does not move.
     */
    @Test
    void moveThroughOpenDoor() {
        Room currentRoom = maze.getRoomAt(0, 0);
        Door rightDoor = currentRoom.getDoor(Direction.RIGHT);

        if (rightDoor != null && rightDoor.isOpen()) {
            // Door is open, so player should move
            player.moveThroughOpenDoor(Direction.RIGHT);

            assertEquals(0, player.getRow(), "Player row should remain 0 after moving right");
            assertEquals(1, player.getCol(), "Player should have moved to column 1");
            assertEquals(maze.getRoomAt(0, 1), player.getCurrentRoom(), "Player should now be in room (0,1)");
        } else {
            // Door is closed or does not exist, player should NOT move
            player.moveThroughOpenDoor(Direction.RIGHT);

            assertEquals(0, player.getRow(), "Player row should remain 0 because door is closed");
            assertEquals(0, player.getCol(), "Player column should remain 0 because door is closed");
            assertEquals(maze.getRoomAt(0, 0), player.getCurrentRoom(), "Player should still be in starting room");
        }
    }

}
