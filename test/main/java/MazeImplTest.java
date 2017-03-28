package main.java;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

// TODO: Move all the config-related info into a rsc file.
public class MazeImplTest {

    private static final String TEST_FILE = "testIO.txt";
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
        this.mazeFactory = new MazeFactoryImpl();
        this.mazeWriter = new MazeWriter(MazeImplTest.TEST_FILE);
        this.mazeReader = new MazeReader(MazeImplTest.TEST_FILE);
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

        JTextArea textArea = new JTextArea(maze.toString(), maze.getDimension(), maze.getDimension());
        JOptionPane.showMessageDialog(null, textArea, "Maze visual representation", JOptionPane.INFORMATION_MESSAGE);

        assertFalse(this.findUninitialisedCells(maze));
    }

    @Test
    @Ignore
    public void testMazeSolver() {
        //
    }

    @Test
    public void testReadWriteForOneMaze() {
        Maze initialMaze = this.mazeFactory.createMazeUsingAlgorithm(new DepthFirstCarver());

        this.mazeWriter.writeMaze(initialMaze, false);
        Maze readMaze = this.mazeReader.readFirstMaze();

        assertTrue(initialMaze.equals(readMaze));
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
}
