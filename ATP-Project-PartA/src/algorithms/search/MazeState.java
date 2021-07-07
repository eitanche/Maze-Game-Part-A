package algorithms.search;

import algorithms.mazeGenerators.Position;

import java.util.Objects;

/**
 * this class represent specific state of maze,
 * a state in the maze is its position.
 */
public class MazeState extends AState{

    private Position p;

    /**
     * constructor
     * @param p Position in maze
     * @param positionValue The value of the given position
     */

    public MazeState(Position p,int positionValue) {
        if (p == null)
            throw new NullPointerException("position is null");
        this.p = p;
        this.positionValue = positionValue;
        this.father = null;
    }

    /**
     * returns this position value
     * @return position value
     */
    public Position getPosition() {
        return p;
    }

    /**
     * hash code for Generics
     * @return hash code for Generics
     */
    @Override
    public int hashCode() {
        return Objects.hash(p);
    }

    /**
     * check if the position is in the same indexes of the maze map (same location)
     * @param other other position to equal
     * @return true if both state seats in the same location
     */
    public boolean equals(Object other) {
        if (other == null)
            throw new NullPointerException("null Object");
        if (!(other instanceof MazeState))
            return false;
        return ((MazeState)other).getPosition().equals(p);
    }

    /**
     * return MazeState as a string
     * @return {x,y}
     */
    public String toString() {
        return "{"+(p.getRowIndex())+","+(p.getColumnIndex())+"}";
    }



}
