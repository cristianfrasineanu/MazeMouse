package main.java;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

// TODO: Move all the config-related info into a rsc file.
public class MazeImplTest {

    private static final String TEST_FILE_NAME = "testIO.txt";
    MazeFactory mazeFactory;
    MazeReader mazeReader;
    MazeWriter mazeWriter;

    public MazeImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.mazeFactory = new MazeFactoryImpl(20, new Coords(0, 4), new Coords(4, 0));
        this.mazeWriter = new MazeWriter(MazeImplTest.TEST_FILE_NAME);
        this.mazeReader = new MazeReader(MazeImplTest.TEST_FILE_NAME);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testMazeGenerationNoWalls() {
        Maze maze = this.mazeFactory.createTestMazeNoWalls();
        Cell entryCell = maze.getCell(maze.getEntryCoordinates());
        Cell exitCell = maze.getCell(maze.getExitCoordinates());

        assertEquals(entryCell.getNumberOfExits(), 1);
        assertEquals(exitCell.getNumberOfExits(), 1);
        assertFalse(this.findUninitialisedCells(maze));
    }

    @Test
    public void testMazeGenerationWithDepthFirst() {
        Maze maze = this.mazeFactory.createMazeUsingAlgorithm(new DepthFirstCarver());

        displayMazeVisually(maze);

        assertFalse(this.findUninitialisedCells(maze));
    }

    private void displayMazeVisually(Maze maze) {
        JTextArea textArea = new JTextArea(maze.toString(), maze.getDimension(), maze.getDimension());
        JOptionPane.showMessageDialog(null, textArea, "Maze visual representation", JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean findUninitialisedCells(Maze maze) {
        boolean foundUninitialised = false;
        for (int line = 0; line < maze.getDimension(); line++) {
            for (int column = 0; column < maze.getDimension(); column++) {
                if (maze.getCell(new Coords(line, column)) == null) {
                    foundUninitialised = true;
                }
            }
        }

        return foundUninitialised;
    }

    // The solver should take into account that the maze cannot be seen from above, and the 
    // traverser will firstly have to "learn" the maze and then persist the most efficient path.
    @Test
    public void testDepthFirstSolver() {
        Maze maze = this.mazeFactory.createMazeUsingAlgorithm(new DepthFirstCarver());

        MazeAlgorithm dfSolver = new DepthFirstSolver(maze);
        dfSolver.go();

        assertTrue(maze.getCurrentCoordinates().equals(maze.getExitCoordinates()));
    }

    @Test
    public void testReadWriteForOneMaze() {
        Maze initialMaze = this.mazeFactory.createMazeUsingAlgorithm(new DepthFirstCarver());

        this.mazeWriter.writeMaze(initialMaze, false);
        Maze readMaze = this.mazeReader.readFirstMaze();

        assertTrue(initialMaze.equals(readMaze));
    }
}
