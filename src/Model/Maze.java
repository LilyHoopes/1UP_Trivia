/*
 * This class represents the maze structure.
 * It handles:
 * - Room and door initialization
 * - Player placement and movement
 * - Win/loss tracking
 */

package Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents the maze structure for the game.
 * Maze responsibilities:
 * - Initialize maze with room objects
 * - link rooms with shared door objects
 * - Track and update player location
 * - Determine win and loss conditions
 * - hold a player instance
 *
 * @author Lily Hoopes
 * @author Komalpreet Dhaliwal
 * @author Christiannel Maningat
 * @version 5/25/2025
 */

public class Maze implements Serializable {

    /** 2D array of rooms forming the maze grid. */
    private final Room[][] myMaze;

    /** Number of rows in the maze. */
    private final int myRows; //number of rows in maze

    /** Number of columns in the maze. */
    private final int myCols; //number of cols in maze

    /** The player navigating the maze. */
    private final Player myPlayer; //instance of a player in the maze

    /** A list of trivia questions to assign to doors. */
    private final ArrayList<TriviaQuestion> myQuestions;

    /**
     * Constructs a maze of given dimensions, loads trivia questions,
     * creates rooms, connects them with doors, and places the player at start.
     *
     * @param theRows number of rows for the maze.
     * @param theCols number of columns for the maze.
     */
    public Maze(final int theRows, final int theCols) {


        myQuestions = QuestionFactory.getQuestions(); // this copies the list in

        myRows = theRows;
        myCols = theCols;
        myMaze = new Room[theRows][theCols]; //make a 2D array that is theRows by theCols big (4x4)

        initializeRooms();
        initializeDoors();

        // Initialize player, will start at (0,0)
        myPlayer = new Player(this);
    }

    /**
     * Returns the player instance navigating this maze.
     *
     * @return the maze player.
     */
    public Player getPlayer() {
        return myPlayer;
    }


    /**
     * Creates room objects for each cell in the grid.
     */
    private void initializeRooms() {
        //creates a new room object for each cell in the maze
        for (int row = 0; row < myRows; row++) {
            for (int col = 0; col < myCols; col++) {
                myMaze[row][col] = new Room(row, col);
            }
        }
    }

    /**
     * Connects adjacent rooms with shared doors and assigns trivia questions.
     * Doors are created only if not already present and questions remain.
     */
    private void initializeDoors() {
        //connect rooms with doors, each door is shared between two rooms
        //example, rooms A's right door is room B's left door, should be same door

        //loop through each room in the grid
        for (int row = 0; row < myRows; row++) {
            for (int col = 0; col < myCols; col++) {
                Room current = myMaze[row][col]; //the current room we are working on

                //for each direction, calculate the position of what would be the adjacent room
                for (Direction dir : Direction.values()) {
                    int newRow = row + dir.getRowOffset();
                    int newCol = col + dir.getColOffset();

                    //check if this room is in bounds, if so, assign the adjacent
                    // to 'neighbor' for each direction
                    if (isInBounds(newRow, newCol)) {
                        Room neighbor = myMaze[newRow][newCol];

                        // Only create and assign a new door if the current room
                        // doesn't already have one in that direction
                        if (current.getDoor(dir) == null) {

                            //TODO there is no doors so the player cant move bc the arraylist is alwasy empty
                            //TODO so it never actually creates the doors in the firt place


                            if (!myQuestions.isEmpty()) {
                                TriviaQuestion singleQuestion = myQuestions.getFirst();
                                myQuestions.removeFirst();
                                Door sharedDoor = new Door(singleQuestion);

                                current.addDoor(dir, sharedDoor);
                                neighbor.addDoor(Direction.getOppositeDirection(dir), sharedDoor);
                            } else {
                                // Handle the case when questions run out.
                                System.err.println("No more trivia questions available for doors!");
                                // You can create a Door without a question, or break/return,
                                // or throw an exception, depending on your app design.
                            }

                        }
                    }
                }
            }
        }
    }

    /**
     * Checks if given coordinates are within maze bounds.
     *
     * @param theRow theRow index.
     * @param theCol column index.
     * @return true if in bounds, false otherwise.
     */
    public boolean isInBounds(final int theRow, final int theCol) {
        //checks to see if the given theRow and theCol are in bounds
        return theRow >= 0 && theRow < myRows && theCol >= 0 && theCol < myCols;
    }

    /**
     * Returns the room where the player currently is.
     *
     * @return current room of the player.
     */
    public Room getCurrentRoom() {
        //returns the room player is currently in
        return myMaze[myPlayer.getRow()][myPlayer.getCol()];
    }

    /**
     * Checks if the player has reached the maze exit.
     *
     * @return true if the game is won, false otherwise.
     */
    public boolean isGameWon() {
        // returns true if player reached exit
        return myPlayer.getRow() == 3 && myPlayer.getCol() == 3;
    }

    /**
     * Determines if the player has lost the game.
     * A loss occurs when all possible directions are either out of bounds or locked.
     * Implements breadth first search.
     *
     * @return true if the game is lost, false otherwise.
     */
    public boolean isGameLost() {
        boolean[][] visited = new boolean[myRows][myCols];
        return !dfs(0, 0, visited);  // Game is lost if there is no path to goal
    }

    private boolean dfs(int row, int col, boolean[][] visited) {
        if (row == myRows - 1 && col == myCols - 1) return true;  // found goal

        visited[row][col] = true;
        Room current = getRoomAt(row, col);

        for (Direction dir : Direction.values()) {
            int newRow = row, newCol = col;
            switch (dir) {
                case UP -> newRow--;
                case DOWN -> newRow++;
                case LEFT -> newCol--;
                case RIGHT -> newCol++;
            }

            // Skip if out of bounds or already visited
            if (newRow < 0 || newCol < 0 || newRow >= myRows || newCol >= myCols) continue;
            if (visited[newRow][newCol]) continue;

            Door door = current.getDoor(dir);
            if (door != null && !door.isLocked()) {
                if (dfs(newRow, newCol, visited)) return true;  // path exists!
            }
        }

        return false;  // no paths lead to goal
    }


    /**
     * Returns the room at specified coordinates if valid.
     *
     * @param theRow target row index.
     * @param theCol target column index.
     * @return the room at given position.
     * @throws IndexOutOfBoundsException if coordinates is out of range.
     */
    public Room getRoomAt(final int theRow, final int theCol) {
        //returns the Room at specific coordinates
        if (isInBounds(theRow, theCol)) {
            return myMaze[theRow][theCol];
        }
        throw new IndexOutOfBoundsException("Room coordinates out of bounds.");
    }
}