/*
 * This class represents the maze structure.
 * It handles:
 * - Room and door initialization
 * - Player placement and movement
 * - Win/loss tracking
 */

package Model;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents the maze structure for the game.
 *
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

        //TODO the arraylist isnt getting poopulatled here?
        System.out.println("Inside maze Constructor");
        myQuestions = QuestionFactory.getQuestions(); // this copies the list in

        //this is empty, so arraylist has nothing in it at this point
        System.out.println("myQuestions arrayList contents: " + myQuestions.toString());

        for (TriviaQuestion q : myQuestions) {
            System.out.println("question: " + q); // or q.getQuestionText() if we want just the text
        }

        myRows = theRows;
        myCols = theCols;
        myMaze = new Room[theRows][theCols]; //make a 2D array that is theRows by theCols big (4x4)

        //TODO its still empty at this point when i try printing it out
        System.out.println("SECOND myQuestions arrayList contents: " + myQuestions.toString());

        initializeRooms();
        initializeDoors();

        //TODO this is STILL empty, idk when it gets populated
        System.out.println("THIRD myQuestions arrayList contents: " + myQuestions.toString());

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

    //TODO it seems that the rooms dont have the doors when they should, testing showed no doors exist anywheres
    //TODO i should probably jut rewrite this entire method bc it confusing, should i hard code in doors to each room?
    //TODO probably should do it as i did but something aint right, figure out the issue

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
                                TriviaQuestion singleQuestion = myQuestions.get(0);
                                myQuestions.remove(0);
                                Door sharedDoor = new Door(singleQuestion);

                                current.addDoor(dir, sharedDoor);
                                neighbor.addDoor(dir.opposite(), sharedDoor);
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