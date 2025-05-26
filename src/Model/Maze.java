package Model;

public class Maze {

    private Room[][] myRooms;
    private final int myRows;
    private final int myCols;

    private int myPlayerRow;
    private int myPlayerColumn;

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

    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < myRows && col >= 0 && col < myCols;
    }

    public Room getCurrentRoom() {
        return myRooms[myPlayerRow][myPlayerColumn];
    }

    //checks is a door exists and is open, then moves the player's coordinates if valid
    private boolean movePlayer(Direction theDirection) {
        Room current = getCurrentRoom();
        Door door = current.getDoor(theDirection);

        //if there is not a door or its closed, we can move through it yet
        if (door == null || !door.isOpen()) {
            return false; // Can't move – no door or door is not open
        }

        int newRow = myPlayerRow + theDirection.getRowOffset();
        int newCol = myPlayerColumn + theDirection.getColOffset();

        if (!isInBounds(newRow, newCol)) {
            return false; // Out of bounds
        }

        // Valid move
        myPlayerRow = newRow;
        myPlayerColumn = newCol;
        return true;
    }

    // returns true if player reached exit
    public boolean isGameWon() {
        return myPlayerRow == 3 && myPlayerColumn == 3;
    }

    //returns true if all possible directions are locked or invalid
    public boolean isGameLost() {
        Room current = getCurrentRoom();

        for (Direction dir : Direction.values()) {
            int newRow = myPlayerRow + dir.getRowOffset();
            int newCol = myPlayerColumn + dir.getColOffset();

            // Only check valid directions
            if (isInBounds(newRow, newCol)) {
                Door door = current.getDoor(dir);
                if (door != null && !door.isLocked()) {
                    return false; // At least one move is still possible
                }
            }
        }
        // All possible directions are either locked or invalid
        return true;
    }

    //do i need this?
    public int[] getRoomPosition() {
        return new int[]{myPlayerRow, myPlayerColumn};
    }
}