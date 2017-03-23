package main.java;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cristianfrasineanu
 */
public class MazeWriter {

    private final String filePath;
    private static final String RSC_PATH = "src/main/resources/";
    private static final Logger LOG = Logger.getLogger(MazeWriter.class.getName());
    private BufferedWriter bufferedWriter;

    public MazeWriter(String fileName) {
        this.filePath = MazeWriter.RSC_PATH + fileName;
    }

    public void writeList() {
        // TODO: Write not just one maze. Contribution anyone?
    }

    public void writeMaze(Maze maze, boolean append) {
        Coords entryCoordinates = maze.getEntryCoordinates();
        Coords exitCoordinates = maze.getExitCoordinates();

        try {
            this.bufferedWriter = new BufferedWriter(new FileWriter(filePath, append));

            this.writeCoordinates(entryCoordinates);
            this.writeCoordinates(exitCoordinates);
            this.writeNumberOfExits(maze);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Error writing to file: {0}", filePath);
        } finally {
            try {
                this.bufferedWriter.close();
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, "Error clossing the write stream.");
            }
        }
    }

    private void writeCoordinates(Coords coordinates) {
        try {
            this.bufferedWriter.write(String.valueOf(coordinates.x));
            this.bufferedWriter.newLine();
            this.bufferedWriter.write(String.valueOf(coordinates.y));
            this.bufferedWriter.newLine();
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Error writing the coordinates to: {0}", filePath);
        }
    }

    private void writeNumberOfExits(Maze maze) {
        try {
            for (int i = 0; i < maze.getDimension(); i++) {
                for (int j = 0; j < maze.getDimension(); j++) {
                    this.bufferedWriter.write(String.valueOf(maze.getCell(new Coords(i, j)).getNumberOfExits()));
                }
                this.bufferedWriter.newLine();
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Error writing the number of exits to: {0}", filePath);
        }
    }
}
