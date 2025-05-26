package Model;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;


/*
Room should:
-repersent a single cell in the maze grid
-stores its location and its doors to adjacent rooms
 */
public class Room implements Serializable {

    private final int myRow;
    private final int myCol;
    private final Map<Direction, Door> myDoors;

    //Creates a new room at a specific row and column in the maze grid
    public Room(final int theRow, final int theCol) {
        myRow = theRow;
        myCol = theCol;
        myDoors = new EnumMap<>(Direction.class);
    }

    //returns row position of room instance
    public int getRow() {
        return myRow;
    }

    //returns column position of room instance
    public int getCol() {
        return myCol;
    }

    //given the direction relative to room, return that door
    public Door getDoor(Direction theDir) {
        return myDoors.get(theDir);
    }

    //Adds a door to this room in a given direction
    public void addDoor(Direction theDir, Door theDoor) {
        myDoors.put(theDir, theDoor);
    }

    //checks if a room already have a door in a given direction
    public boolean hasDoor(final Direction theDirection) {
        return myDoors.containsKey(theDirection);
    }

    //idk if i need this
//    public boolean isValidRoom() {
//        for (Door door : myDoors.values()) {
//            if (door.getState() != DoorState.LOCKED) {
//                return true;
//            }
//        }
//        return false;
//    }

    //Checks whether the adjacent room in the given direction is accessible (door isnt locked)
    public boolean isAdjacentRoomValid(Direction theDir) {
        Door door = myDoors.get(theDir);
        return door != null && door.getState() != DoorState.LOCKED;
    }
}
