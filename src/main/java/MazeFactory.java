package main.java;

interface MazeFactory {

    public Maze createTestMazeNoWalls();

    public Maze createMazeUsingAlgorithm(MazeAlgorithm carver);
}
