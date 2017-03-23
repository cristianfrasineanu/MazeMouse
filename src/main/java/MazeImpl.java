package main.java;

import java.util.Objects;

/**
 * A maze is a collection of cells that are interconnected and contains at least
 * a possible path for exit.
 *
 * @author cristianfrasineanu
 */
public class MazeImpl implements Maze {

    public static int MAX_DIMENSION = 40;
    public static int MIN_DIMENSION = 10;

    private final Coords entryCoordinates;
    private final Coords exitCoordinates;
    private final Cell[][] cellNetwork;
    private int currentX;
    private int currentY;
    private boolean initialised = false;

    public MazeImpl(Cell[][] cellNetwork, Coords entryCoordinates, Coords exitCoordinates) {
        this.cellNetwork = cellNetwork;
        this.entryCoordinates = entryCoordinates;
        this.exitCoordinates = exitCoordinates;
    }

    @Override
    public void printCurrentPosition() {
        System.out.println("Currently at (counting from 1): " + (this.currentX + 1) + ", " + (this.currentY + 1));
    }

    @Override
    public void moveMouseToEntry() {
        this.currentX = this.entryCoordinates.x;
        this.currentY = this.entryCoordinates.y;
        this.initialised = true;
    }

    @Override
    public Cell getCell(Coords cellCoordinates) {
        return this.cellNetwork[cellCoordinates.x][cellCoordinates.y];
    }

    @Override
    public int getDimension() {
        return this.cellNetwork.length;
    }

    @Override
    public Coords getEntryCoordinates() {
        return this.entryCoordinates;
    }

    @Override
    public Coords getExitCoordinates() {
        return this.exitCoordinates;
    }

    @Override
    public String toString() {
        if (initialised) {
            StringBuilder stringMaze = new StringBuilder();

            for (Cell[] networkLine : cellNetwork) {
                for (Cell cell : networkLine) {
                    stringMaze.append(cell.isTraversable() ? Cell.pathGlyph : Cell.wallGlyph);
                }
                stringMaze.append(System.getProperty("line.separator"));
            }

            if (this.initialised) {
                stringMaze.replace(this.getTotalOffset() + Cell.wallGlyph.length() - 1, this.getTotalOffset() + Cell.wallGlyph.length(), Character.toString((char) 0x2606));
            }

            return stringMaze.toString();
        } else {
            return "Please construct the maze first!";
        }
    }

    @Override
    public String showExits() {
        if (initialised) {
            StringBuilder stringMaze = new StringBuilder();

            for (Cell[] networkLine : cellNetwork) {
                for (Cell cell : networkLine) {
                    stringMaze.append(cell.getNumberOfExits()).append(" ");
                }
                stringMaze.append(System.getProperty("line.separator"));
            }

            return stringMaze.toString();
        } else {
            return "Please construct the maze first!";
        }
    }

    private int getTotalOffset() {
        int lineOffset = Cell.wallGlyph.length() * currentX * this.getDimension();
        int columnOffset = Cell.wallGlyph.length() * currentY;
        int separatorOffset = currentX * (System.getProperty("line.separator").length());

        return lineOffset + columnOffset + separatorOffset;
    }

    @Override
    public boolean equals(Object otherObj) {
        if (otherObj instanceof Maze) {
            Maze theOtherMaze = (Maze) otherObj;

            if (this.getDimension() != theOtherMaze.getDimension()) {
                return false;
            }
            if (!theOtherMaze.getEntryCoordinates().equals(this.entryCoordinates) || !theOtherMaze.getExitCoordinates().equals(this.exitCoordinates)) {
                return false;
            }

            boolean areAllCellsExitsEqual = true;

            for (int line = 0; line < theOtherMaze.getDimension(); line++) {
                for (int column = 0; column < theOtherMaze.getDimension(); column++) {
                    int currentExits = this.cellNetwork[line][column].getNumberOfExits();
                    int otherExits = this.cellNetwork[line][column].getNumberOfExits();
                    if (currentExits != otherExits) {
                        areAllCellsExitsEqual = false;
                    }
                }
            }

            return areAllCellsExitsEqual;
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.entryCoordinates);
        hash = 71 * hash + Objects.hashCode(this.exitCoordinates);
        hash = 71 * hash + this.currentX;
        hash = 71 * hash + this.currentY;
        return hash;
    }
}
