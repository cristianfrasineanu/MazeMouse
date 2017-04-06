package main.java;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MazeWriter {

    private final String filePath;
    private static final String RSC_PATH = "src/main/resources/";
    private static final Logger LOG = Logger.getLogger(MazeWriter.class.getName());
    private BufferedWriter bufferedWriter;

    public MazeWriter(String fileName) {
        this.filePath = MazeWriter.RSC_PATH + fileName;

        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, "Couldn't create the file: ", filePath);
            }
        }
    }

    public void writeList(List<Maze> mazes) {
        // TODO: Write a list of mazes.
    }

    public void writeMaze(Maze maze, boolean append) {
        Coords entryCoordinates = maze.getEntryCoordinates();
        Coords exitCoordinates = maze.getExitCoordinates();

        try {
            this.bufferedWriter = new BufferedWriter(new FileWriter(filePath, append));
            this.writeCoordinates(entryCoordinates);
            this.writeCoordinates(exitCoordinates);
            this.writeDimension(maze);
            this.writeCells(maze);

            LOG.log(Level.INFO, "Wrote a maze to: {0}", filePath);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Error writing to: {0}", filePath);
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
            this.bufferedWriter.write(coordinates.x + " " + coordinates.y);
            this.bufferedWriter.newLine();
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Error writing the coordinates to: {0}", filePath);
        }
    }

    private void writeDimension(Maze maze) {
        try {
            this.bufferedWriter.write(String.valueOf(maze.getDimension()));
            this.bufferedWriter.newLine();
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Error writing the maze dimension to: {0}", filePath);
        }
    }

    private void writeCells(Maze maze) {
        try {
            for (int i = 0; i < maze.getDimension(); i++) {
                for (int j = 0; j < maze.getDimension(); j++) {
                    this.bufferedWriter.write((maze.getCell(new Coords(i, j)).isTraversable() ? 0 : "X") + " ");
                }
                this.bufferedWriter.newLine();
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Error writing the number of exits to: {0}", filePath);
        }
    }
}
