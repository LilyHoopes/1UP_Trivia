package Model;

public class Room extends Maze {
    private Map<Direction, Door> myDoors;

    public Room() {
        // initialize door map
    }

    public boolean isValidRoom() {
        // determine if room is valid
    }

    public boolean isAdjacentRoomValid(Direction dir) {
        // checks if adjacent room in a direction is valid
    }
}
