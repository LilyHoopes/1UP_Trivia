/*
 * Represents a single cell in the maze grid, storing its location,
 * doors to adjacent rooms, and its display icon.
 *
 * Serializable for saving/loading game state.
 */

package Model;

import javax.swing.*;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

/**
 * Represents a single cell in the maze grid, holding its position,
 * a map of doors to adjacent rooms, and an optional icon for UI.
 *
 * @author Lily Hoopes
 * @author Komalpreet Dhaliwal
 * @author Christiannel Maningat
 * @version 5/17/2025
 */
public class Room implements Serializable {

    /** The row index of this room in the maze. */
    private final int myRow; //row position of room

    /** The column index of this room in the maze. */
    private final int myCol; //col position of room

    /** Map from directions to doors for adjacent room connections. */
    private final Map<Direction, Door> myDoors; //each room has a map that holds direction to a door

    /** Icon representing this room in the UI. */
    private ImageIcon myIcon;

    /**
     * Constructs a room at the specified grid coordinates.
     *
     * @param theRow the row index of the room.
     * @param theCol the column index of the room.
     */
    public Room(final int theRow, final int theCol) {
        //Creates a new room at a specific row and column in the maze grid
        myRow = theRow;
        myCol = theCol;
        myDoors = new EnumMap<>(Direction.class);
    }

//    public void setIcon(final String theIcon) {
//        myIcon = theIcon;
//    }



    /**
     * Returns the icon image for this room.
     *
     * @return the ImageIcon for display,
     * or null if none are set.
     */
    public ImageIcon getIcon() {
        return myIcon;
    }

    /**
     * Returns the row index of this room.
     *
     * @return the row index.
     */
    public int getRow() {
        //returns row position of room instance
        return myRow;
    }

    /**
     * Returns the column index of this room.
     *
     * @return the column index.
     */
    public int getCol() {
        //returns column position of room instance
        return myCol;
    }

    /**
     * Retrieves the door in the specified direction.
     *
     * @param theDir the direction of the door to get.
     * @return the Door object, or null if none exists.
     */
    public Door getDoor(final Direction theDir) {
        //given the direction relative to room, return that door
        return myDoors.get(theDir); //theDir is key in map, will return the door in that direction
    }

    /**
     * Adds a door connection in the given direction.
     *
     * @param theDir the direction of the new door.
     * @param theDoor the Door object to add.
     */
    public void addDoor(final Direction theDir, final Door theDoor) {
        //Adds a door to this room in a given direction
        myDoors.put(theDir, theDoor); //using theDir key, adds theDoor connected to that direction
    }

    /**
     * Indicates whether this room has a door in the given direction.
     *
     * @param theDirection the direction to check.
     * @return true if a door exists, false otherwise.
     */
    public boolean hasDoor(final Direction theDirection) {
        //checks if a room already have a door in a given direction
        return myDoors.containsKey(theDirection);
    }

    /**
     * Checks whether the adjacent room in the specified direction is accessible
     * in which the door is not locked.
     *
     * @param theDir the direction to check.
     * @return true if adjacent room is valid, false if locked or no door.
     */
    public boolean isAdjacentRoomValid(final Direction theDir) {
        //Checks whether the adjacent room in the given
        //direction is accessible (door isn't locked)
        Door door = myDoors.get(theDir);
        return door.getState() != DoorState.LOCKED;
    }

}
