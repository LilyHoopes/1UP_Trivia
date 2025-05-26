package Model;

/*
Maze should:
-initialize maze with room objects
-link rooms with shared door objects
-track and update player location
-determine win and loss conditions
-hold a player instance
 */


import java.awt.*;
import java.io.Serializable;

public class Maze implements Serializable {


    private final Room[][] myMaze;
    private final int myRows; //number of rows in maze
    private final int myCols; //number of cols in maze
    private final Player myPlayer; //instance of a player in the maze

    // constructor: initialize a maze with the number of rows and cols
    public Maze(final int theRows, final int theCols) {
        myRows = theRows;
        myCols = theCols;
        myMaze = new Room[theRows][theCols]; //make a 2D array that is theRows by theCols big (4x4)

        initializeRooms();
        initializeDoors();

        // Initialize player, will start at (0,0)
        myPlayer = new Player(this);
    }

    public Player getPlayer() {
        return myPlayer;
    }

    //creates a new room object for each cell in the maze
    private void initializeRooms() {
        for (int row = 0; row < myRows; row++) {
            for (int col = 0; col < myCols; col++) {
                myMaze[row][col] = new Room(row, col);
            }
        }
    }

    //connect rooms with doors, each door is shared between two rooms
    //example, rooms A's right door is room B's left door, should be same door
    private void initializeDoors() {
        //loop through each room in the grid
        for (int row = 0; row < myRows; row++) {
            for (int col = 0; col < myCols; col++) {
                Room current = myMaze[row][col]; //the current room we are working on

                //for each direction, calculate the position of what would be the adjacent room
                for (Direction dir : Direction.values()) {
                    int newRow = row + dir.getRowOffset();
                    int newCol = col + dir.getColOffset();

                    //check if this room is in bounds, if so, assign the adjacent to 'neighbor'
                    if (isInBounds(newRow, newCol)) {
                        Room neighbor = myMaze[newRow][newCol];

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

    //checks to see if the given row and col are in bounds
    boolean isInBounds(int row, int col) {
        return row >= 0 && row < myRows && col >= 0 && col < myCols;
    }

    //returns the room player is currently in
    public Room getCurrentRoom() {
        return myMaze[myPlayer.getRow()][myPlayer.getCol()];
    }

    //returns the Room at specific coordinates
    public Room getRoomAt(final int theRow, final int theCol) {
        if (isInBounds(theRow, theCol)) {
            return myMaze[theRow][theCol];
        }
        throw new IndexOutOfBoundsException("Room coordinates out of bounds.");
    }



}