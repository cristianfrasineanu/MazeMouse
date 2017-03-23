package main.java;

import java.util.Random;

/**
 * We make use of a carver that implements MazeAlgorithm in order to model the
 * actual maze cell structure for a maze having a path. All you have to do is to
 * provide go().
 *
 * @author cristianfrasineanu
 */
public class MazeFactoryImpl implements MazeFactory {
    
    private final Coords entryCoordinates = new Coords();
    private final Coords exitCoordinates = new Coords();
    private Cell[][] cellNetwork;
    
    public MazeFactoryImpl() {
        this.generateRandomNetworkSkeleton();
    }
    
    @Override
    public Maze createMazeWithPath() {
        System.out.println(entryCoordinates.x + " " + entryCoordinates.y);
        System.out.println(exitCoordinates.x + " " + exitCoordinates.y);
        
        MazeAlgorithm carver = new DepthFirstCarver(cellNetwork, entryCoordinates, exitCoordinates);
        carver.go();
        this.setNumberOfExitsForAllCells();
        
        Maze maze = new MazeImpl(cellNetwork, entryCoordinates, exitCoordinates);
        
        return maze;
    }
    
    @Override
    public Maze createTestMazeNoWalls() {
        this.fillNetworkNoWalls();
        this.setNumberOfExitsForAllCells();
        
        Maze maze = new MazeImpl(cellNetwork, entryCoordinates, exitCoordinates);
        
        return maze;
    }

    // Use this method to get a general template for a maze. 
    // Please create a separate method for getting a network layout with specific inputs like dimension and entry coordinates.
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

        // We don't want the exit and the entry on the same side, it wouldn't be very original.
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
    
    private void setNumberOfExitsForAllCells() {
        for (int line = 1; line < cellNetwork.length - 1; line++) {
            for (int column = 1; column < cellNetwork[line].length - 1; column++) {
                if (cellNetwork[line][column].isTraversable()) {
                    cellNetwork[line][column].setNumberOfExits(this.getNumberOfExits(line, column));
                } else {
                    cellNetwork[line][column].setNumberOfExits(0);
                }
            }
        }
    }
    
    private int getNumberOfExits(int lineIndex, int columnIndex) {
        return ((cellNetwork[lineIndex + 1][columnIndex].isTraversable() ? 1 : 0)
                + (cellNetwork[lineIndex - 1][columnIndex].isTraversable() ? 1 : 0)
                + (cellNetwork[lineIndex][columnIndex - 1].isTraversable() ? 1 : 0)
                + (cellNetwork[lineIndex][columnIndex + 1].isTraversable() ? 1 : 0));
    }
}
