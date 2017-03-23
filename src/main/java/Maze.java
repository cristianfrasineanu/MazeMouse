package main.java;

/**
 *
 * @author cristianfrasineanu
 */
public interface Maze {

    public void printCurrentPosition();

    public void moveMouseToEntry();
    
    public Cell getCell(Coords cellCoordinates);
    
    public int getDimension();
    
    public Coords getEntryCoordinates();
    
    public Coords getExitCoordinates();
    
    public String showExits();
}