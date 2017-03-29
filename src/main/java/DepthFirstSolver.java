package main.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class DepthFirstSolver implements MazeAlgorithm {

    private final Maze maze;
    private final Stack<Coords> mouseTrail;
    private Coords currentCoordinates;

    public DepthFirstSolver(Maze maze) {
        this.maze = maze;
        this.mouseTrail = new Stack<>();
        this.currentCoordinates = new Coords(this.maze.getEntryCoordinates().x, this.maze.getEntryCoordinates().y);
        this.mouseTrail.push(this.currentCoordinates);
    }

    @Override
    public void go() {
        this.setNumberOfExitsForEntryAndExit();
        this.departFromMargin();
        this.traverseMaze();
        this.mouseTrail.clear();
    }

    private void setNumberOfExitsForEntryAndExit() {
        this.maze.getCell(this.maze.getEntryCoordinates()).setNumberOfExits(1);
        this.maze.getCell(this.maze.getExitCoordinates()).setNumberOfExits(1);
    }

    public void departFromMargin() {
        if (this.currentCoordinates.x == 0) {
            ++this.currentCoordinates.x;
        } else if (this.currentCoordinates.x == this.maze.getDimension() - 1) {
            --this.currentCoordinates.x;
        } else if (this.currentCoordinates.y == 0) {
            ++this.currentCoordinates.y;
        } else {
            --this.currentCoordinates.y;
        }
    }

    private void traverseMaze() {
        this.mouseTrail.push(this.currentCoordinates);
        
        while (!this.currentCoordinates.equals(this.maze.getExitCoordinates())) {
            this.currentCoordinates = this.mouseTrail.pop();
            this.maze.setCurrentCoordinates(this.currentCoordinates);

            if (!this.currentCoordinates.equals(this.maze.getExitCoordinates())) {
                this.maze.getCell(this.currentCoordinates).setNumberOfExits(this.getNumberExitsForCell());

                List<Coords> neighbours = new ArrayList<>(Arrays.asList(generateNeighbours()));
                neighbours.forEach((cellCoordinates) -> {
                    if (isNeighbourFeasibleOnTraversal(cellCoordinates)) {
                        this.mouseTrail.push(cellCoordinates);
                    }
                });
            }
        }
    }

    private boolean isNeighbourFeasibleOnTraversal(Coords cell) {
        return (!checkIfOnMargin(cell)
                && this.maze.getCell(cell).getNumberOfExits() == -1)
                || cell.equals(this.maze.getExitCoordinates());
    }

    private Coords[] generateNeighbours() {
        Coords[] neighbours = {
            new Coords(this.currentCoordinates.x - 1, this.currentCoordinates.y),
            new Coords(this.currentCoordinates.x, this.currentCoordinates.y + 1),
            new Coords(this.currentCoordinates.x + 1, this.currentCoordinates.y),
            new Coords(this.currentCoordinates.x, this.currentCoordinates.y - 1)
        };

        return neighbours;
    }

    private boolean checkIfOnMargin(Coords cell) {
        return cell.x == 0
                || cell.x == this.maze.getDimension() - 1
                || cell.y == 0
                || cell.y == this.maze.getDimension() - 1;
    }

    private int getNumberExitsForCell() {
        Coords upCell = new Coords(this.currentCoordinates.x - 1, this.currentCoordinates.y);
        Coords rightCell = new Coords(this.currentCoordinates.x, this.currentCoordinates.y + 1);
        Coords downCell = new Coords(this.currentCoordinates.x + 1, this.currentCoordinates.y);
        Coords leftCell = new Coords(this.currentCoordinates.x, this.currentCoordinates.y - 1);

        return ((this.maze.getCell(upCell).isTraversable() ? 1 : 0)
                + (this.maze.getCell(rightCell).isTraversable() ? 1 : 0)
                + (this.maze.getCell(downCell).isTraversable() ? 1 : 0)
                + (this.maze.getCell((leftCell)).isTraversable() ? 1 : 0));
    }
}
