package Controller;
import View.GameView;
import Model.Maze;

public class GameController {

    private boolean myGameWon;
    private Maze myMaze;
    private GameView myView;
    private Player myPlayer;

    public static void main(String[] args) {
        // launch the game
    }

    public boolean getGameWon() {
        return myGameWon;
    }

    public void setGameWon(final boolean theGameWon) {
        myGameWon = theGameWon;
    }

    public void executeMove(Direction theDirection) {
        // move player logic
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
