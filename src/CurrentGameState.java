import java.io.Serializable;

public class CurrentGameState extends Maze implements Serializable {
    private String myType; // could be enum instead

    public String getState() {
        return myType;
    }
}
