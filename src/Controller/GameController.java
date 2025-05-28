package Controller;
import View.GameView;
import Model.*;

import java.awt.*;

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
            view.setController(controller); // 🔥 this connects the two

            // Display first question
            TriviaQuestion currentQuestion = controller.getCurrentQuestion();
            if (currentQuestion != null) {
                view.setQuestion(currentQuestion);
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
            view.setController(controller);
        });

    }

    public void checkGameStatus(Maze theMaze) {
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
    public boolean checkAnswer(String theAnswer) {
        if (myCurrentQuestion != null) {
            boolean correct = myCurrentQuestion.isCorrect(theAnswer);

            // Move to next question after checking
            myCurrentQuestion = myQuestionFactory.getNextQuestion();
            return correct;
        }
        return false;
    }

    public void startGame() {
        // initialize game logic
    }

    public void saveGame() {
        // serialize CurrentGameState
    }

    public void loadGame() {
        // deserialize CurrentGameState
    }

    public void restartGame() {
        // reset game state
    }
}