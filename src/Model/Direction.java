package Model;

public enum Direction {
    NORTH(-1, 0), //-1 moves us up 1, which is North
    EAST(0, 1), //1 moves us right 1, which is East
    SOUTH(1, 0), //1 moves us down 1, which is South
    WEST(0, -1); //-1 moves us left 1, which is West

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
            case NORTH: return SOUTH;
            case SOUTH: return NORTH;
            case EAST:  return WEST;
            case WEST:  return EAST;
            default: throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}
