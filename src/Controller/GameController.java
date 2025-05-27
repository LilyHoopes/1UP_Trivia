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
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {

                Maze maze = new Maze(4, 4);
                Player player = maze.getPlayer();

                final GameView view = new GameView(maze);

                // Try moving right — this will only work if the door is open
                boolean moved = player.move(Direction.LEFT);

                if (moved) {
                    System.out.println("Moved RIGHT to: (" + player.getRow() + ", " + player.getCol() + ")");
                } else {
                    System.out.println("Could NOT move RIGHT, door closed or out of bounds.");
                }
                //Maze.getInstance().addPropertyChangeListener(view);
                GameController controller = new GameController();
                controller.setView(view);
            }
        });
    }

    public GameController() {
        myQuestionFactory = new QuestionFactory("trivia_questions.db");
        myCurrentQuestion = myQuestionFactory.getNextQuestion();
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
    public void checkAnswer(String theAnswer) {
        if (myCurrentQuestion != null) {
            boolean correct = myCurrentQuestion.isCorrect(theAnswer);
            if (correct) {
                System.out.println("Correct answer!");
                // Advance logic if needed
            } else {
                System.out.println("Wrong answer.");
            }

            // Load next question after answer
            myCurrentQuestion = myQuestionFactory.getNextQuestion();
        }
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