package Model;

public enum Direction {
    UP(-1, 0), //-1 moves us up 1, which is North
    RIGHT(0, 1), //1 moves us right 1, which is East
    DOWN(1, 0), //1 moves us down 1, which is South
    LEFT(0, -1); //-1 moves us left 1, which is West

    private final int myRowOffset;
    private final int myColOffset;

    Direction(final int theRowOffset, final int theColOffset) {
        myRowOffset = theRowOffset;
        myColOffset = theColOffset;
    }

    public int getRowOffset() {
        return myRowOffset;
    }

    public int getColOffset() {
        return myColOffset;
    }

    public Direction opposite() {
        switch (this) {
            case UP: return DOWN;
            case DOWN: return LEFT;
            case RIGHT:  return UP;
            case LEFT:  return RIGHT;
            default: throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}
