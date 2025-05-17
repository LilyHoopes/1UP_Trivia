package Model;

//a room has doors, so this is compositions
//a maze has rooms, so this is also composition

import java.util.EnumMap;
import java.util.Map;

// represents a single room in the maze
//each room can have 2-4 doors (corners, sides, inside)
public class Room {

    private final int myRow;
    private final int myCol;
    private final Map<Direction, Door> myDoors;

    //constructor that makes a room at specified position with no doors yet
    public Room(final int theRow, final int theCol) {
        myRow = theRow;
        myCol = theCol;
        myDoors = new EnumMap<Direction, Door>(Direction.class);
    }

    public int getRow() {
        return myRow;
    }

    public int getCol() {
        return myCol;
    }

    public boolean isValidRoom() {
        // determine if room is valid
        return false;
    }

    public boolean isAdjacentRoomValid(Direction dir) {
        // checks if adjacent room in a direction is valid
        return false;
    }
}
