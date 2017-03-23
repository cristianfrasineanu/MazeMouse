package main.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cristianfrasineanu
 */
public class MazeReader {

    private final String filePath;
    private static final String RSC_PATH = "src/main/resources/";
    private static final Logger LOG = Logger.getLogger(MazeReader.class.getName());
    private BufferedReader bufferedReader;

    public MazeReader(String fileName) throws FileNotFoundException {
        this.filePath = MazeReader.RSC_PATH + fileName;

        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("The file must be existing.");
        }
    }

    public List<Maze> readAll() {
        // TODO: Just implement it.

        return Collections.emptyList();
    }

    public Maze readFirstMaze() {
        try {
            this.bufferedReader = new BufferedReader(new FileReader(filePath));

            Coords entryCoordinates = new Coords(), exitCoordinates = new Coords();
            this.setEntryAndExitCoordinates(entryCoordinates, exitCoordinates);

            Cell[][] cellNetwork = this.buildCellNetwork();
            Maze maze = new MazeImpl(cellNetwork, entryCoordinates, exitCoordinates);
            maze.moveMouseToEntry();

            return maze;
        } catch (FileNotFoundException ex) {
            LOG.log(Level.SEVERE, "Couldn't open the file {0}", filePath);
        } finally {
            try {
                this.bufferedReader.close();
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, "Couldn't close the reading stream.");
            }
        }

        return null;
    }

    private void setEntryAndExitCoordinates(Coords entryCoordinates, Coords exitCoordinates) {
        try {
            entryCoordinates.x = Integer.parseInt(this.bufferedReader.readLine());
            entryCoordinates.y = Integer.parseInt(this.bufferedReader.readLine());
            exitCoordinates.x = Integer.parseInt(this.bufferedReader.readLine());
            exitCoordinates.y = Integer.parseInt(this.bufferedReader.readLine());
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Couldn't read the coordinates.");
        }
    }

    private Cell[][] buildCellNetwork() {
        try {
            int dimension, currentX = 0, currentY = 0, numberOfExits;
            boolean traversable;

            String lineBuffer = this.bufferedReader.readLine();
            dimension = lineBuffer.length();
            Cell[][] cellNetwork = new Cell[dimension][dimension];
            while (currentX < dimension) {
                while (currentY < dimension) {
                    numberOfExits = Character.getNumericValue(lineBuffer.charAt(currentY));
                    traversable = numberOfExits != 0;
                    cellNetwork[currentX][currentY++] = new Cell(traversable);
                }
                lineBuffer = this.bufferedReader.readLine();
                currentY = 0;
                ++currentX;
            }

            return cellNetwork;
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Couldn't read the number of exits.");
        }

        return null;
    }
}
