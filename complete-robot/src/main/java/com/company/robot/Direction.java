package robot;

public enum Direction {
    NORTH, WEST, SOUTH, EAST;

    public static Direction oppositeDirection(Direction direction) {
        switch (direction) {
            case NORTH: return SOUTH;
            case SOUTH: return NORTH;
            case WEST:return EAST;
            case EAST:return WEST;
            default:return null;
        }
    }
}
