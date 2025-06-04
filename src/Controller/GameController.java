/*
 * The GameController class manages the core logic of the Maze Trivia Game.
 * It acts as the bridge between the Model and View layers, handling user input,
 * checking for trivia answers, managing game progress, and coordinating the state
 * changes. This controller is responsible for loading questions, evaluating answers,
 * tracking game status (win/loss), and saving/loading the game state.
 *
 * This class follows the MVC design pattern and helps maintain separation of concerns,
 * allowing the View to remain UI-focused and the Model to handle data related tasks.
 */

package Controller;
import View.GameView;
import Model.*;

import javax.swing.*;
import java.awt.*;

/**
 * Controller class for the Maze Trivia Game, handling game flow,
 * question retrieval, and interaction between the model and view layers.
 *
 * @author Lily Hoopes
 * @author Komalpreet Dhaliwal
 * @author Christiannel Maningat
 * @version 5/7/2025
 */
public class GameController {

    private static final GameController INSTANCE = new GameController();

    /** Flag showing whether the game has been won or not. */
    private boolean myGameWon;

    private Maze myMaze;
    private Player myPlayer;
    private GameView myView;

    private Door myPendingDoor;
    private Direction myPendingDirection;

    /** Factory for loading trivia questions. */
    private QuestionFactory myQuestionFactory;

    // Public method to access the singleton instance
    public static GameController getInstance() {
        return INSTANCE;
    }

    /**
     * Entry point for the application. Initializes MVC components,
     * loads the first trivia question, and sets up the UI.
     *
     * @param theArgs command-line arguments.
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(() -> {
            GameView.showTitleScreen(() -> {
                GameController controller = GameController.getInstance();

                Maze maze = new Maze(4,4);
                Player player = maze.getPlayer();
                GameView view = new GameView(maze); // <-- launches full game window
                controller.setView(view);
                view.setController(controller);

                controller.setMaze(maze);
                controller.setPlayer(player);

                controller.saveGame();
                controller.loadGame();
            });
        });
    }

    /**
     * Constructs a GameController, initializes the question factory
     * and loads the first trivia question from the database.
     */
    public GameController() {

        myQuestionFactory = new QuestionFactory("src/trivia_questions.db");
        //myView = new GameView(this, myMaze); // assume GameView accepts controller and maze

        if (myQuestionFactory.getNextQuestion() == null) {
            System.err.println("No questions loaded. Check your database!");
        }
    }

    public void setMaze(final Maze theMaze) {
        myMaze = theMaze;
    }

    public void setPlayer(final Player thePlayer) {
        myPlayer = thePlayer;
    }

    /**
     * Sets the view component for this controller.
     *
     * @param theView the GameView to associate with this controller.
     */
    public void setView(final GameView theView) {
        myView = theView;
    }

    /**
     * Returns whether the game has been won.
     *
     * @return true if the game is won, false otherwise.
     */
    public boolean getGameWon() {
        return myGameWon;
    }

    /**
     * Sets the game-won flag.
     *
     * @param theGameWon true to mark the game as won, false otherwise.
     */
    public void setGameWon(final boolean theGameWon) {
        myGameWon = theGameWon;
    }

    /**
     * Checks whether the user's answer to the pending door's question is correct.
     * If the answer is correct, the door is opened and the player moves through it.
     * If incorrect, the door is locked. In both cases, the pending door and direction
     * are cleared after the check.
     *
     * @param theUserAnswer the answer selected by the user.
     * @return true if the answer was correct and the player moved, false otherwise.
     */
    public boolean checkAnswerAndMove(String theUserAnswer) {

        if (myPendingDoor == null) return false; // Safety

        //System.out.println("theUserAnswer = " + theUserAnswer);
        //System.out.println("pendingDoor: " + myPendingDoor.toString());
        //System.out.println("pendingDirection: " + myPendingDirection);

        if (myPendingDoor == null || myPendingDirection == null) {
            System.err.println("No pending door/direction/question set.");

            return false;
        }

        boolean correct = (myPendingDoor.getQuestion().isCorrect(theUserAnswer));
        //System.out.println("checkAnswerAndMove boolean correct: " + correct);

        if (correct) {
            //System.out.println("myPendingDoor state before: " + myPendingDoor.getState());
            myPendingDoor.openDoor();
            //System.out.println("myPendingDoor state after: " + myPendingDoor.getState());
            myPlayer.moveThroughOpenDoor(myPendingDirection);
            myView.handleMoveThroughOpenDoor(myPendingDirection);
        } else {
            myPendingDoor.lockDoor();
        }

        myPendingDoor = null;
        myPendingDirection = null;

        myView.updateMovementButtons ();

        return correct;
    }

    /**
     * Displays a win/loss message dialog based on the player's game status.
     * This method should be called from the View after a move is processed.
     *
     * @param theComponent the parent component for the JOptionPane (e.g., the GameView).
     */
    public void checkGameWinLossStatus(final Component theComponent) {
        ImageIcon winIcon = new ImageIcon("icons/marioIsHAPPY.png");
        ImageIcon loseIcon = new ImageIcon("icons/marioIsSAD.png");

        if (myMaze.isGameWon()) {
            //System.out.println("Game won! this is in handleMove method");
            JOptionPane.showMessageDialog(theComponent, "You won the game!",
                    "Congratulations!", JOptionPane.INFORMATION_MESSAGE, winIcon);
        } else if (myMaze.isGameLost()) {
            //System.out.println("Game lost! this is in handleMove method");
            JOptionPane.showMessageDialog(theComponent, "You're trapped! Game over!",
                    "Uh oh!", JOptionPane.INFORMATION_MESSAGE, loseIcon);
        }
    }

    /**
     * Attempts to move the player in the specified direction.
     * If the door in that direction is open, the player moves and the view updates.
     * If the door is closed, the associated trivia question is displayed.
     * If the door is locked, the player is notified and cannot proceed.
     *
     * @param theDirection the direction the player wants to move.
     */
    public void attemptMove(Direction theDirection) {

        //System.out.println("inside the attemptMove method in controller");

        Room currentRoom = myMaze.getRoomAt(myPlayer.getRow(), myPlayer.getCol());
        Door targetDoor = currentRoom.getDoor(theDirection);

        if (targetDoor == null) {
            JOptionPane.showMessageDialog(null, "You can't move this way!", "Movement Blocked", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //for testing
        //printDoorsForCurrentRoom(currentRoom);

        //for testing
        /*
        System.out.println("➡️ Door direction attempted: " + theDirection);
        System.out.println("➡️ Door state: " + targetDoor.getState());
        System.out.println("➡️ Trivia Question from this door: " + targetDoor.getQuestion());
        System.out.println("➡️ Correct answer for this door: " + targetDoor.getQuestion().getCorrectAnswer());
         */

        switch (targetDoor.getState()) {

            case OPEN:
                myPlayer.moveThroughOpenDoor(theDirection);
                myView.handleMoveThroughOpenDoor(theDirection); // GUI update
                //checkGameStatus(final Maze theMaze);
                //clearTriviaPanel(); // optional: reset question panel
                break;

            case CLOSED:
                //System.out.println("Door is closed. Showing trivia question...");
                myPendingDoor = targetDoor;
                myPendingDirection = theDirection;
                TriviaQuestion question = targetDoor.getQuestion();
                //System.out.println("Question for this door: " + question);
                myView.displayTriviaQuestion(question);
                break;

            case LOCKED:
                //System.out.println("Door is locked.");
                JOptionPane.showMessageDialog(null, "You can't move this way!", "Movement Blocked", JOptionPane.WARNING_MESSAGE);
                break;
        }

        checkGameWinLossStatus(myView);
        myView.updateMovementButtons();

    }

    //For testing
    /**
     * Prints the state of all doors (UP, DOWN, LEFT, RIGHT) in the given room.
     * This is primarily used for debugging purposes.
     *
     * @param currentRoom the room whose doors' states will be printed.
     */
    public void printDoorsForCurrentRoom(Room currentRoom) {
        //System.out.println("Doors in the current room:\n");
        Door northDoor = currentRoom.getDoor(Direction.UP);
        //System.out.println("UP door:\n");
        if (northDoor != null) northDoor.printState();

        Door southDoor = currentRoom.getDoor(Direction.DOWN);
        //System.out.println("DOWN door:\n");
        if (southDoor != null) southDoor.printState();

        Door eastDoor = currentRoom.getDoor(Direction.LEFT);
        //System.out.println("LEFT door:\n");
        if (eastDoor != null) eastDoor.printState();

        Door westDoor = currentRoom.getDoor(Direction.RIGHT);
        //System.out.println("RIGHT door:\n");
        if (westDoor != null) westDoor.printState();
    }

    /**
     * Initializes and starts the game logic.
     */
    public void startGame() {
        // initialize game logic
    }

    /**
     * Saves the current game state to storage.
     */
    public void saveGame() {
        // serialize CurrentGameState
    }

    /**
     * Loads the game state from persistent storage.
     */
    public void loadGame() {
        // deserialize CurrentGameState
    }

}
