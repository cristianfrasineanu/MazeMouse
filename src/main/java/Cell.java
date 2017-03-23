package main.java;

/**
 * The cell through which our traverser will pass each time through the maze. It
 * can be whether a free cell or an occupied one (acting as a wall).
 *
 * @author cristianfrasineanu
 */
public class Cell {

    public static String wallGlyph = Character.toString((char) 0x2589);
    public static String pathGlyph = Character.toString((char) 0x2504);
    
    private boolean isTraversable;
    private int numberOfExits;

    public Cell(boolean isTraversable) {
        this.isTraversable = isTraversable;

        if (!isTraversable) {
            this.numberOfExits = 0;
        }
    }

    public boolean isTraversable() {
        return isTraversable;
    }

    public void passVia() {
        --this.numberOfExits;
        checkIfStillTraversable();
    }

    private void checkIfStillTraversable() {
        if (this.numberOfExits == 0) {
            this.isTraversable = false;
        }
    }

    public void setNumberOfExits(int numberOfExits) {
        this.numberOfExits = numberOfExits;
    }

    public int getNumberOfExits() {
        return numberOfExits;
    }

    public static enum possibleDirections {
        NORTH,
        EAST,
        SOUTH,
        WEST;

        public static possibleDirections getOpposite(possibleDirections direction) {
            possibleDirections opposite = null;
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
}
