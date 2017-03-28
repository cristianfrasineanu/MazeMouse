package main.java;

import java.util.Random;

/**
 * We make use of a carver that implements MazeAlgorithm in order to model the
 * actual structure for a maze to have a path.
 */
public class MazeFactoryImpl implements MazeFactory {

    private final Coords entryCoordinates = new Coords();
    private final Coords exitCoordinates = new Coords();
    private Cell[][] cellNetwork;

    public MazeFactoryImpl() {
        this.generateRandomNetworkSkeleton();
    }

    @Override
    public Maze createMazeUsingAlgorithm(MazeAlgorithm carver) {
        this.passCellNetworkToCarver(carver);
        carver.go();
        this.setNumberOfExitsForAllCells();
        
        Maze maze = new MazeImpl(cellNetwork, entryCoordinates, exitCoordinates);

        return maze;
    }

    private void passCellNetworkToCarver(MazeAlgorithm carver) {
        carver.setCellNetwork(cellNetwork);
        carver.setCurrentCoordinates(entryCoordinates);
        carver.setExitCoordinates(exitCoordinates);
    }
    
    @Override
    public Maze createTestMazeNoWalls() {
        this.fillNetworkNoWalls();
        this.setNumberOfExitsForAllCells();

        Maze maze = new MazeImpl(cellNetwork, entryCoordinates, exitCoordinates);

        return maze;
    }

    private void generateRandomNetworkSkeleton() {
        Random randomGenerator = new Random();
        int randomNetworkDimension = randomGenerator.nextInt(MazeImpl.MAX_DIMENSION - MazeImpl.MIN_DIMENSION) + MazeImpl.MIN_DIMENSION;
        this.cellNetwork = new Cell[randomNetworkDimension][randomNetworkDimension];

        this.generateRandomEntryAndExit(randomNetworkDimension);
        this.setEntryAndExitCell();
        this.fillNetworkBorder();
    }

    private void generateRandomEntryAndExit(int dimension) {
        Random randomGenerator = new Random();

        this.entryCoordinates.x = randomGenerator.nextInt(dimension - 1);
        if (this.entryCoordinates.x != 0 && this.entryCoordinates.x != dimension - 1) {
            this.entryCoordinates.y = (randomGenerator.nextInt() % 2 == 0) ? 0 : dimension - 1;
        } else {
            this.entryCoordinates.y = randomGenerator.nextInt(dimension - 3) + 1;
        }

        // We subjectively or compulsively chose to have the entry and the exit on different sides.
        // In real life there's seldom a case where we have the entry and the exit on the same side.
        this.exitCoordinates.x = randomGenerator.nextInt(dimension - 1);
        if (this.exitCoordinates.x != 0 && this.exitCoordinates.x != dimension - 1) {
            this.exitCoordinates.y = (this.entryCoordinates.y == 0) ? dimension - 1 : 0;
        } else {
            this.exitCoordinates.x = (this.entryCoordinates.x == 0) ? dimension - 1 : 0;
            this.exitCoordinates.y = randomGenerator.nextInt(dimension - 3) + 1;
        }
    }

    private void setEntryAndExitCell() {
        cellNetwork[this.entryCoordinates.x][this.entryCoordinates.y] = new Cell(true);
        cellNetwork[this.entryCoordinates.x][this.entryCoordinates.y].setNumberOfExits(1);
        cellNetwork[this.exitCoordinates.x][this.exitCoordinates.y] = new Cell(true);
        cellNetwork[this.exitCoordinates.x][this.exitCoordinates.y].setNumberOfExits(1);
    }

    private void fillNetworkBorder() {
        for (int line = 0; line < cellNetwork.length; line++) {
            for (int column = 0; column < cellNetwork[line].length; column++) {
                if (cellNetwork[line][column] == null) {
                    if (line == 0 || line == cellNetwork.length - 1
                            || column == 0 || column == cellNetwork[0].length - 1) {
                        cellNetwork[line][column] = new Cell(false);
                    }
                }
            }
        }
    }

    private void fillNetworkNoWalls() {
        for (int line = 1; line < cellNetwork.length - 1; line++) {
            for (int column = 1; column < cellNetwork[line].length - 1; column++) {
                cellNetwork[line][column] = new Cell(true);
            }
        }
    }
    
    // TODO: Move the logic at the solver level.
    private void setNumberOfExitsForAllCells() {
        for (int line = 1; line < cellNetwork.length - 1; line++) {
            for (int column = 1; column < cellNetwork[line].length - 1; column++) {
                if (cellNetwork[line][column].isTraversable()) {
                    cellNetwork[line][column].setNumberOfExits(this.getNumberExitsForCell(line, column));
                } else {
                    cellNetwork[line][column].setNumberOfExits(0);
                }
            }
        }
    }

    private int getNumberExitsForCell(int lineIndex, int columnIndex) {
        return ((cellNetwork[lineIndex + 1][columnIndex].isTraversable() ? 1 : 0)
                + (cellNetwork[lineIndex - 1][columnIndex].isTraversable() ? 1 : 0)
                + (cellNetwork[lineIndex][columnIndex - 1].isTraversable() ? 1 : 0)
                + (cellNetwork[lineIndex][columnIndex + 1].isTraversable() ? 1 : 0));
    }
}
