package main.java;

public enum Direction {
    NORTH, EAST, SOUTH, WEST;

    public static Direction getOpposite(Direction direction) {
        Direction opposite = null;
        if (direction != null) {
            switch (direction) {
                case NORTH:
                    opposite = SOUTH;
                    break;
                case EAST:
                    opposite = WEST;
                    break;
                case SOUTH:
                    opposite = NORTH;
                    break;
                case WEST:
                    opposite = EAST;
                    break;
            }
        }

        return opposite;
    }

}
