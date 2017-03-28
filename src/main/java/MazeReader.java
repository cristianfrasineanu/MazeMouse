package main.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MazeReader {

    private final String filePath;
    private static final String RSC_PATH = "src/main/resources/";
    private static final Logger LOG = Logger.getLogger(MazeReader.class.getName());
    private Scanner mazeScanner;

    public MazeReader(String fileName) {
        this.filePath = MazeReader.RSC_PATH + fileName;

        File file = new File(filePath);
        if (!file.exists()) {
            LOG.log(Level.SEVERE, "Please firstly create the file to be read from.");
        }
    }

    public List<Maze> readAll() {
        // TODO: Read until the end of the file and return a corresponding collection.

        return Collections.emptyList();
    }

    public Maze readFirstMaze() {
        try {
            this.mazeScanner = new Scanner(new BufferedReader(new FileReader(filePath))).useDelimiter("\\s");

            Coords entryCoordinates = new Coords(), exitCoordinates = new Coords();
            this.setEntryAndExitCoordinates(entryCoordinates, exitCoordinates);

            Cell[][] cellNetwork = this.buildCellNetwork();
            Maze maze = new MazeImpl(cellNetwork, entryCoordinates, exitCoordinates);

            return maze;
        } catch (FileNotFoundException ex) {
            LOG.log(Level.SEVERE, "Couldn't open the file {0}", filePath);
        } finally {
            this.mazeScanner.close();
        }

        return null;
    }

    private void setEntryAndExitCoordinates(Coords entryCoordinates, Coords exitCoordinates) {
        entryCoordinates.x = this.mazeScanner.nextInt();
        entryCoordinates.y = this.mazeScanner.nextInt();
        exitCoordinates.x = this.mazeScanner.nextInt();
        exitCoordinates.y = this.mazeScanner.nextInt();
    }

    private Cell[][] buildCellNetwork() {
        int dimension, currentX = 0, currentY = 0;
        boolean traversable;

        dimension = this.mazeScanner.nextInt();
        Cell[][] cellNetwork = new Cell[dimension][dimension];
        while (currentX < dimension && this.mazeScanner.hasNextLine()) {
            while (currentY < dimension) {
                traversable = (this.mazeScanner.nextInt() == 1);
                cellNetwork[currentX][currentY++] = new Cell(traversable);
            }
            currentY = 0;
            ++currentX;
            this.mazeScanner.nextLine();
        }

        return cellNetwork;
    }
}
