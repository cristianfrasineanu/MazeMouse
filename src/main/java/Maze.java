package main.java;

public interface Maze {

    public void printCurrentPosition();
    
    public Cell getCell(Coords cellCoordinates);
    
    public int getDimension();
    
    public Coords getEntryCoordinates();
    
    public Coords getExitCoordinates();
    
    public String showExits();

    Coords getCurrentCoordinates();

    void setCurrentCoordinates(Coords currentCoordinates);
}
