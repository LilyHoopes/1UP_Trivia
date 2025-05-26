package Model;

/*
Maze should:
-initialize maze with room objects
-link rooms with shared door objects
-track and update player location
-determine win and loss conditions
-
 */


import java.io.Serializable;

public class Maze implements Serializable {

    private final Room[][] myRooms;
    private final int myRows;
    private final int myCols;

//
//    private boolean myGameWon;
//    private Direction[] myDirections = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
//    private Room myStartRoom;
//    private Room myExitRoom;
//    private TriviaQuestion myQuestion;

    // constructor: initialize maze, start and exit rooms, directions, etc.
    public Maze(final int theRows, final int theCols) {
        myRows = theRows;
        myCols = theCols;
        myRooms = new Room[theRows][theCols];

        initializeRooms();
        initializeDoors();
    }

    //creates a new room object for each cell in the maze
    private void initializeRooms() {
        for (int row = 0; row < myRows; row++) {
            for (int col = 0; col < myCols; col++) {
                myRooms[row][col] = new Room(row, col);
            }
        }
    }

    //connect rooms with doors, each door is shared between two rooms
    //example, rooms A's right door is room B's left door, should be same door
    private void initializeDoors() {
        //loop through each room in the grid
        for (int row = 0; row < myRows; row++) {
            for (int col = 0; col < myCols; col++) {
                Room current = myRooms[row][col]; //the current room we are working on

                //for each direction, calculate the position of what would be the adjacent room
                for (Direction dir : Direction.values()) {
                    int newRow = row + dir.getRowOffset();
                    int newCol = col + dir.getColOffset();

                    //check if this room is in bounds, if so, assign the adjacent to 'neighbor'
                    if (isInBounds(newRow, newCol)) {
                        Room neighbor = myRooms[newRow][newCol];

                        // Only create and assign a new door if the current room doesn't already have one in that direction
                        if (current.getDoor(dir) == null) {
                            Door sharedDoor = new Door(TriviaQuestion.getRandomQuestion());

                            //TODO this is where questions are pulled from the trivia question class
                            //TODO the line is commented out for now

                            //add door to current room in dir direction
                            current.addDoor(dir, sharedDoor);
                            neighbor.addDoor(dir.opposite(), sharedDoor);
                        }
                    }
                }
            }
        }
    }

    boolean isInBounds(int row, int col) {
        return row >= 0 && row < myRows && col >= 0 && col < myCols;
    }

    //returns the current Room
    public Room getCurrentRoom() {
        return myRooms[myPlayerRow][myPlayerColumn];
    }

    //returns the Room at specific coordinates
    public Room getRoomAt(final int theRow, final int theCol) {
        if (isInBounds(theRow, theCol)) {
            return myRooms[theRow][theCol];
        }
        throw new IndexOutOfBoundsException("Room coordinates out of bounds.");
    }
}