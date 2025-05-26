package Controller;
import View.GameView;
import Model.*;

import java.awt.*;

public class GameController {

    private boolean myGameWon;
    //private Maze myMaze;
    private GameView myView;


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
            }
        });
    }



    public boolean getGameWon() {
        return myGameWon;
    }

    public void setGameWon(final boolean theGameWon) {
        myGameWon = theGameWon;
    }

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
