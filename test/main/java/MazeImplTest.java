package main.java;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author cristianfrasineanu
 */
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

        Cell cell = maze.getCell(maze.getEntryCoordinates());
        assertEquals(cell.getNumberOfExits(), 1);

        cell = maze.getCell(maze.getExitCoordinates());
        assertEquals(cell.getNumberOfExits(), 1);

        assertFalse(this.findUninitialisedCells(maze));
    }

    @Test
    public void testMazeGenerationWithPath() {
        Maze maze = this.mazeFactory.createMazeWithPath();

        JTextArea textArea = new JTextArea(maze.toString(), maze.getDimension(), maze.getDimension());
        JOptionPane.showMessageDialog(null, textArea, "Maze visual configuration", JOptionPane.INFORMATION_MESSAGE);

        assertFalse(this.findUninitialisedCells(maze));
    }

    @Test
    public void testReadWriteForOneMaze() {
        Maze initialMaze = this.mazeFactory.createMazeWithPath();

        this.mazeWriter.writeMaze(initialMaze, false);
        Maze readMaze = this.mazeReader.readFirstMaze();

        assertTrue(initialMaze.equals(readMaze));
    }

    private boolean findUninitialisedCells(Maze maze) {
        boolean foundUninitialised = false;
        for (int line = 0; line < maze.getDimension(); line++) {
            for (int column = 0; column < maze.getDimension(); column++) {
                if (maze.getCell(new Coords(line, column)).getNumberOfExits() == -1) {
                    foundUninitialised = true;
                }
            }
        }

        return foundUninitialised;
    }
}
