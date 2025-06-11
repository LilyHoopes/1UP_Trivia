package Test;

import Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Room class.
 * Tests getters, door management, and adjacency checks.
 *
 * @author Lily Hoopes
 * @author Komalpreet Dhaliwal
 * @author Christiannel Maningat
 * @version 6/10/2025
 */
class RoomTest {

    /** Room instance used in tests. */
    private Room room;

    /**
     * Sets up a Room at row 1, column 2 before each test.
     */
    @BeforeEach
    void setUp() {
        room = new Room(1, 2);  // Create a Room at row 1, col 2
    }

    /**
     * Tests that getRow() returns the correct row.
     */
    @Test
    void testGetRow() {
        assertEquals(1, room.getRow());
    }

    /**
     * Tests that getCol() returns the correct column.
     */
    @Test
    void testGetCol() {
        assertEquals(2, room.getCol());
    }

    /**
     * Tests adding a door and retrieving it by direction.
     * Also verifies no door returns null.
     */
    @Test
    void testAddAndGetDoor() {
        TriviaQuestion question = new TriviaQuestion(
                "Which power-up makes Mario invincible?",
                new String[]{"Mushroom", "Fire Flower", "Super Star", "1-Up"},
                "Super Star"
        );
        Door door = new Door(question);

        room.addDoor(Direction.UP, door);
        assertSame(door, room.getDoor(Direction.UP));
        assertNull(room.getDoor(Direction.DOWN));  // no door in this direction
    }

    /**
     * Tests the hasDoor() method for directions with and without doors.
     */
    @Test
    void testHasDoor() {
        Door door = new Door(new TriviaQuestion("?", new String[]{"A", "B", "C", "D"}, "A"));
        room.addDoor(Direction.LEFT, door);

        assertTrue(room.hasDoor(Direction.LEFT));
        assertFalse(room.hasDoor(Direction.RIGHT));
    }

    /**
     * Tests if adjacent rooms are valid based on door states (OPEN vs LOCKED).
     */
    @Test
    void testIsAdjacentRoomValid() {
        Door unlockedDoor = new Door(new TriviaQuestion("?", new String[]{"A", "B", "C", "D"}, "A"));
        unlockedDoor.openDoor();  // set door state to OPEN

        Door lockedDoor = new Door(new TriviaQuestion("?", new String[]{"A", "B", "C", "D"}, "A"));
        lockedDoor.lockDoor();  // set door state to LOCKED

        room.addDoor(Direction.UP, unlockedDoor);
        room.addDoor(Direction.DOWN, lockedDoor);

        assertTrue(room.isAdjacentRoomValid(Direction.UP));    // OPEN door
        assertFalse(room.isAdjacentRoomValid(Direction.DOWN)); // LOCKED door
    }

    /**
     * Tests getDoorDirections() returns correct set of directions with doors.
     */
    @Test
    void testGetDoorDirections() {
        assertTrue(room.getDoorDirections().isEmpty());

        Door door = new Door(new TriviaQuestion("?", new String[]{"A", "B", "C", "D"}, "A"));
        room.addDoor(Direction.RIGHT, door);

        assertEquals(1, room.getDoorDirections().size());
        assertTrue(room.getDoorDirections().contains(Direction.RIGHT));
    }

    /**
     * Tests that the room's icon is initially null.
     */
    @Test
    void testGetIconInitiallyNull() {
        assertNull(room.getIcon());
    }
}
