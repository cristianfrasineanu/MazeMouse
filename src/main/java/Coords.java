package main.java;

/**
 *
 * @author cristianfrasineanu
 */
public class Coords {

    int x;
    int y;

    public Coords() {
        this.x = -1;
        this.y = -1;
    }
    
    public Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Coords) {
            Coords otherCoord = (Coords) other;
            return (otherCoord.x == x) && (otherCoord.y == y);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.x;
        hash = 11 * hash + this.y;
        return hash;
    }
}
