package Model;

import java.io.Serializable;

/**
 * Stores the state of the game including the maze and player
 * for saving and loading purposes.
 */
public class CurrentGameState implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Maze myMaze;
    private final Player myPlayer;

    /**
     * Constructs a CurrentGameState with the given maze and player.
     *
     * @param theMaze the current maze state.
     * @param thePlayer the current player state.
     */
    public CurrentGameState(final Maze theMaze, final Player thePlayer) {
        myMaze = theMaze;
        myPlayer = thePlayer;
    }

    public Maze getMaze() {
        return myMaze;
    }

    public Player getPlayer() {
        return myPlayer;
    }
}