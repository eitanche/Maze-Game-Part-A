package algorithms.maze3D;


import algorithms.search.AState;


import java.util.Objects;

public class Maze3DState extends AState {
    private Position3D p;

    /**
     * constructor
     * @param p 3D Position in 3D maze
     * @param positionValue The value of the given position
     */

    public Maze3DState(Position3D p,int positionValue) {
        if (p == null)
            throw new NullPointerException("position arg is null");
        this.p = p;
        this.positionValue = positionValue;
        this.father = null;
    }

    public Position3D getPosition() {
        return p;
    }
    /**
     * check if the position is in the same indexes of the maze map (same location)
     * @param other other position to equal
     * @return true if both state seats in the same location
     */
    public boolean equals(Object other) {
        if (other == null)
            throw new NullPointerException("other arg is null");
        if (!(other instanceof Maze3DState))
            return false;
        return ((Maze3DState)other).getPosition().equals(p);
    }
    /**
     * return MazeState as a string
     * @return {z,x,y}
     */
    public String toString() {
        return "{"+p.getDepthIndex()+","+(p.getRowIndex())+","+(p.getColumnIndex())+"}";
    }


    /**
     * hash code for Generics
     * @return hash code for Generics
     */
    @Override
    public int hashCode() {
        return Objects.hash(p);
    }
}
