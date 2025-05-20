package Controller;
import View.GameView;
//import Model.Maze;

import java.awt.*;

public class GameController {

    private boolean myGameWon;
    //private Maze myMaze;
    private GameView myView;
    //private Player myPlayer;

    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(new Runnable() {
            /**
             * Sets up the CrapsView and connects it to the Craps model
             * using a PropertyChangeListener for event-driven updates.
             */
            @Override
            public void run() {
                final GameView view = new GameView();
                //Craps.getInstance().addPropertyChangeListener(view);
            }
        });
    }

    public boolean getGameWon() {
        return myGameWon;
    }

    public void setGameWon(final boolean theGameWon) {
        myGameWon = theGameWon;
    }

//    public void executeMove(Direction theDirection) {
//        // move player logic
//    }

    public void checkAnswer(String theAnswer) {
        // validate trivia answer
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


/*
*  ADD SOMETHING LIKE.........
public GameController() {
        // Provide path to your SQLite DB file
        myQuestionFactory = new QuestionFactory("resources/trivia_questions.db");
    }

    public TriviaQuestion getNextQuestion() {
        return myQuestionFactory.getNextQuestion();

* */
