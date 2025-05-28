package Model;

import java.io.Serializable;

public class CurrentGameState extends Maze implements Serializable {
    private static final long serialVersionUID = 1L;

    private String myState;  // Replacing GameStatus with a simple string

    public CurrentGameState(String state, int rows, int cols) {
        super(rows, cols); // Maze constructor
        this.myState = state;
    }

    public String getState() {
        return myState;
    }

    public void setState(String state) {
        this.myState = state;
    }
}

