package main.java;

/**
 * Makes a good use of the command pattern, letting us to decouple the logic behind
 * the operation we want to execute on a Maze (as receiver).
 */
public interface MazeAlgorithm {

    public void go();
}
