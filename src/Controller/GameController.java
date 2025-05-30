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

    /** Flag showing whether the game has been won or not. */
    private boolean myGameWon;

    private Maze myMaze;
    private Player myPlayer;
    private GameView myView;

    private Door myPendingDoor;
    private Direction myPendingDirection;

    /** Factory for loading trivia questions. */
    private QuestionFactory myQuestionFactory;

    /** The current trivia question to display and check. */
    private TriviaQuestion myCurrentQuestion;

    /**
     * Entry point for the application. Initializes MVC components,
     * loads the first trivia question, and sets up the UI.
     *
     * @param theArgs command-line arguments.
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(() -> {

            GameController controller = new GameController();
            Maze maze = new Maze(4,4);
            Player player = maze.getPlayer();
            GameView view = new GameView(maze);
            controller.setView(view);
            view.setController(controller);

            controller.setMaze(maze);
            controller.setPlayer(player);

            //for testing
            //QuestionFactory.printQuestions();

            //TODO we dont wanna show the first question until the user tries to move into a new room, facing a door
            // Display first question
//            TriviaQuestion currentQuestion = controller.getCurrentQuestion();
//            if (currentQuestion != null) {
//                view.setQuestion(currentQuestion);
//            }

//            // Try moving
//            boolean moved = player.move(Direction.LEFT);
//            if (moved) {
//                System.out.println("Moved LEFT to: (" + player.getRow() + ", " + player.getCol() + ")");
//                controller.checkGameStatus(maze);
//            } else {
//                System.out.println("Could NOT move LEFT, door closed or out of bounds.");
//                controller.checkGameStatus(maze);
//            }


            // Save & Load Game
            controller.saveGame();
            controller.loadGame();
            //maybe goes here??
        });

    }

    /**
     * Constructs a GameController, initializes the question factory
     * and loads the first trivia question from the database.
     */
    public GameController() {

        myQuestionFactory = new QuestionFactory("src/trivia_questions.db");
        myCurrentQuestion = myQuestionFactory.getNextQuestion();
        //myView = new GameView(this, myMaze); // assume GameView accepts controller and maze

        if (myCurrentQuestion == null) {
            System.err.println("No questions loaded. Check your database!");
            // Optionally, load some default questions or handle gracefully
        }
    }

    public void setMaze(final Maze theMaze) {
        myMaze = theMaze;
    }

    public void setPlayer(final Player thePlayer) {
        myPlayer = thePlayer;
    }

    /**
     * Checks the player's status in the maze and notifies the view
     * if the game has been won or lost.
     *
     * @param theMaze the Maze model to check for win/loss conditions.
     */
    public void checkGameStatus(final Maze theMaze) {
        System.out.println("in checkGameStatus");
        Player player = theMaze.getPlayer();
        if (player.isGameWon()) {
            System.out.println("Game won!");
            //myView.showGameWon();  // Add this method to GameView to show a message
        } else if (player.isGameLost()) {
            System.out.println("Game lost!");
            //myView.showGameLost(); // Add this to GameView too
        }
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
     * Retrieves the current trivia question.
     *
     * @return the current TriviaQuestion, or null if none
     */
    public TriviaQuestion getCurrentQuestion() {
        // Call this to get the current trivia question!
        return myCurrentQuestion;
    }

    //TODO LILYFIXHERE
    //TODO the probolem is that the label says the correct answer but for some reason the actaul value
    //is the correct answer but for the question from the other door in the room
    //TODO i dont think pending door and direction are actually holding any value so you should print those out
    //TODO check if the isCorrect method is working, put a print statement there too
    public boolean checkAnswerAndMove(String theAnswer) {
        if (myPendingDoor == null || myPendingDirection == null || myCurrentQuestion == null) {
            System.err.println("No pending door/direction/question set.");
            return false;
        }

        //TODO LILYFIXHERE
        boolean correct = myCurrentQuestion.isCorrect(theAnswer);
        System.out.println("checkAnswerAndMove boolean correct: " + correct);

        if (correct) {
            myPendingDoor.openDoor();
            myPlayer.moveThroughOpenDoor(myPendingDirection);
            myView.handleMoveThroughOpenDoor(myPendingDirection);
        } else {
            myPendingDoor.lockDoor();
        }

        myCurrentQuestion = myQuestionFactory.getNextQuestion(); // next trivia for next door
        myPendingDoor = null;
        myPendingDirection = null;

        return correct;
    }


    public void attemptMove(Direction theDirection) {

        System.out.println("inside the attemptMove method in controller");

        Room currentRoom = myMaze.getRoomAt(myPlayer.getRow(), myPlayer.getCol());
        Door targetDoor = currentRoom.getDoor(theDirection);

        if (targetDoor == null) {
            System.out.println("No door in that direction.");
            return;
        }

        //for testing
        printDoorsForCurrentRoom(currentRoom);

        //TODO where should i call checkGameStatus?
        switch (targetDoor.getState()) {

            case OPEN:
                myPlayer.moveThroughOpenDoor(theDirection);
                myView.handleMoveThroughOpenDoor(theDirection); // GUI update
                //checkGameStatus(final Maze theMaze);
                //clearTriviaPanel(); // optional: reset question panel
                break;

            case CLOSED:
                System.out.println("Door is closed. Showing trivia question...");
                myPendingDoor = targetDoor;
                myPendingDirection = theDirection;
                TriviaQuestion myCurrentQuestion = targetDoor.getQuestion();
                System.out.println("Question for this door: " + myCurrentQuestion);
                myView.displayTriviaQuestion(myCurrentQuestion);
                break;

            case LOCKED:
                System.out.println("Door is locked.");
                // optionally update GUI with message
                break;
        }

//
//        //if door is locked, pull the question for that door and set to view
//        //should a different method unlock the door if answered correct, yes I think
//        if (door.getState() == DoorState.CLOSED) {
//            // Set current question from the door
//            myCurrentQuestion = door.getQuestion();
//            myView.setQuestion(myCurrentQuestion);
//            return;
//        }

    }

    //for testing
    public void printDoorsForCurrentRoom(Room currentRoom) {
        System.out.println("Doors in the current room:\n");
        Door northDoor = currentRoom.getDoor(Direction.UP);
        System.out.println("UP door:\n");
        if (northDoor != null) northDoor.printState();

        Door southDoor = currentRoom.getDoor(Direction.DOWN);
        System.out.println("DOWN door:\n");
        if (southDoor != null) southDoor.printState();

        Door eastDoor = currentRoom.getDoor(Direction.LEFT);
        System.out.println("LEFT door:\n");
        if (eastDoor != null) eastDoor.printState();

        Door westDoor = currentRoom.getDoor(Direction.RIGHT);
        System.out.println("RIGHT door:\n");
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

    /**
     * Restarts the game by resetting state and loading initial settings.
     */
    public void restartGame() {
        // reset game state
    }
}