package main.java;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * We make use of a carver that implements MazeAlgorithm in order to model the
 * actual structure for a maze to have a path. A carver should accept 3
 * parameters in order to model the network.
 */
public class MazeFactoryImpl implements MazeFactory {

    private final Coords entryCoordinates = new Coords();
    private final Coords exitCoordinates = new Coords();
    private Cell[][] cellNetwork;

    public MazeFactoryImpl() {
        this.generateRandomNetworkSkeleton();
    }

    private void generateRandomNetworkSkeleton() {
        Random randomGenerator = new Random();
        int randomNetworkDimension = randomGenerator.nextInt(MazeImpl.MAX_DIMENSION - MazeImpl.MIN_DIMENSION) + MazeImpl.MIN_DIMENSION;
        this.cellNetwork = new Cell[randomNetworkDimension][randomNetworkDimension];

        this.generateRandomEntryAndExit(randomNetworkDimension);
        this.enableEntryAndExit();
        this.fillNetworkBorder();
    }

    @Override
    public Maze createMazeUsingAlgorithm(MazeAlgorithm carver) {
        carver = reinstantiateCarverWithParams(carver);
        carver.go();

        Maze maze = new MazeImpl(cellNetwork, entryCoordinates, exitCoordinates);

        return maze;
    }

    private MazeAlgorithm reinstantiateCarverWithParams(MazeAlgorithm carver) {
        try {
            Constructor carverConstructor = carver.getClass().getConstructor(Cell[][].class, Coords.class, Coords.class);
            carver = (MazeAlgorithm) carverConstructor.newInstance(this.cellNetwork, entryCoordinates, exitCoordinates);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(MazeFactoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return carver;
    }

    @Override
    public Maze createTestMazeNoWalls() {
        this.fillNetworkNoWalls();

        Maze maze = new MazeImpl(cellNetwork, entryCoordinates, exitCoordinates);
        maze.getCell(entryCoordinates).setNumberOfExits(1);
        maze.getCell(exitCoordinates).setNumberOfExits(1);

        return maze;
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

    private void enableEntryAndExit() {
        cellNetwork[this.entryCoordinates.x][this.entryCoordinates.y] = new Cell(true);
        cellNetwork[this.exitCoordinates.x][this.exitCoordinates.y] = new Cell(true);
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
}
