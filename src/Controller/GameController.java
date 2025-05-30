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

    private static Maze myMaze;
    private static Player myPlayer;
    private static GameView myView;

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
            myMaze = new Maze(4,4);
            myPlayer = myMaze.getPlayer();
            myView = new GameView(myMaze);
            controller.setView(myView);
            myView.setController(controller); // this connects the two

            QuestionFactory.printQuestions();

            // Display first question
            TriviaQuestion currentQuestion = controller.getCurrentQuestion();
            if (currentQuestion != null) {
                myView.setQuestion(currentQuestion);
            }

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
            myView.setController(controller);
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

    /**
     * Validates the user's answer against the current trivia question,
     * advances to the next question, and returns the correctness.
     *
     * @param theAnswer the user's answer to check.
     * @return true if the answer is correct, false otherwise.
     */
    public boolean checkAnswer(final String theAnswer) {
        // Call this to validate a user's answer
        if (myCurrentQuestion != null) {
            boolean correct = myCurrentQuestion.isCorrect(theAnswer);
            System.out.println(theAnswer + " <-- this answer was selected, is: " + correct);
            if (correct) {
                //unlock door

            } else {
                //lock door
            }

            // Move to next question after checking
            myCurrentQuestion = myQuestionFactory.getNextQuestion();
            return correct;
        }
        return false;
    }

    private void printCurrentRoomDoorStates() {
        Room currentRoom = myMaze.getRoomAt(myPlayer.getRow(), myPlayer.getCol());
        System.out.println("Player is in room at (" + myPlayer.getRow() + ", " + myPlayer.getCol() + ")");
        for (Direction dir : Direction.values()) {
            Door door = currentRoom.getDoor(dir);
            if (door != null) {
                System.out.println("Door to " + dir);
                System.out.println("  State: " + door.getState());
                TriviaQuestion question = door.getQuestion();
                if (question != null) {
                    System.out.println("  Question: " + question.getQuestionText());
                } else {
                    System.out.println("  No question assigned.");
                }
            } else {
                System.out.println("No door to " + dir);
            }
        }
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