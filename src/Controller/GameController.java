package Controller;
import View.GameView;
import Model.*;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class GameController {

    private boolean myGameWon;
    //private Maze myMaze;
    private GameView myView;
    private QuestionFactory myQuestionFactory;
    private TriviaQuestion myCurrentQuestion;


    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(() -> {
            Maze maze = new Maze(4, 4);
            Player player = maze.getPlayer();

            final GameView view = new GameView(maze);
            GameController controller = new GameController();
            controller.setView(view);
            view.setController(controller);

            // Display first question
            TriviaQuestion currentQuestion = controller.getCurrentQuestion();
            if (currentQuestion != null) {
                view.setQuestion(currentQuestion);
            }

            // Try moving
            boolean moved = player.move(Direction.LEFT);
            if (moved) {
                System.out.println("Moved LEFT to: (" + player.getRow() + ", " + player.getCol() + ")");
            } else {
                System.out.println("Could NOT move LEFT, door closed or out of bounds.");
            }

            // Save & Load Game
            controller.saveGame();
            controller.loadGame();
            //maybe goes here??
            view.setController(controller);
        });

    }


    public GameController() {
        myQuestionFactory = new QuestionFactory("src/trivia_questions.db");

        myCurrentQuestion = myQuestionFactory.getNextQuestion();
        if (myCurrentQuestion == null) {
            System.err.println("No questions loaded. Check your database!");
            // Optionally, load some default questions or handle gracefully
        }
    }

    public void setView(GameView theView) {
        myView = theView;
    }

    public boolean getGameWon() {
        return myGameWon;
    }

    public void setGameWon(final boolean theGameWon) {
        myGameWon = theGameWon;
    }


    // Call this to get the current trivia question
    public TriviaQuestion getCurrentQuestion() {
        return myCurrentQuestion;
    }

    // Call this to validate a user's answer
//    public void checkAnswer(String theAnswer) {
//        if (myCurrentQuestion != null) {
//            boolean correct = myCurrentQuestion.isCorrect(theAnswer);
//            if (correct) {
//                System.out.println("Correct answer!");
//                // Advance logic if needed
//            } else {
//                System.out.println("Wrong answer.");
//            }
//
//            // Load next question after answer
//            myCurrentQuestion = myQuestionFactory.getNextQuestion();
//        }
//    }
    public boolean checkAnswer(String theAnswer) {
        if (myCurrentQuestion != null) {
            boolean correct = myCurrentQuestion.isCorrect(theAnswer);

            // Move to next question after checking
            myCurrentQuestion = myQuestionFactory.getNextQuestion();
            return correct;
        }
        return false;
    }


    public void saveGame() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("savegame.dat"))) {
            CurrentGameState state = new CurrentGameState("IN_PROGRESS", 4, 4);
            out.writeObject(state);
            System.out.println("✅ Game state saved.");
        } catch (IOException e) {
            System.err.println("❌ Failed to save game: " + e.getMessage());
        }
    }


    public void loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("savegame.dat"))) {
            CurrentGameState loadedState = (CurrentGameState) in.readObject();
            System.out.println("✅ Game state loaded. Status: " + loadedState.getState());

            // You could also restore maze/player state here
            // e.g., this.myMaze = loadedState;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("❌ Failed to load game: " + e.getMessage());
        }
    }


    public void restartGame() {
        // reset game state
    }

}