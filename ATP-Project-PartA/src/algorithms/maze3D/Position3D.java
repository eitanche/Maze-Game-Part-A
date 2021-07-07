package algorithms.maze3D;

import java.util.Objects;
/**
 * Represents a 3D position in the 3D maze
 */
public class Position3D {
    private int row;
    private int column;
    private int depth;
    /**
     * creates a new position object
     * @param depth the depth index of the position
     * @param row the row index of the position
     * @param column the col index of the position
     */
    public Position3D(int depth,int row, int column) {
     /*   if (depth < 0 || row < 0 || column < 0)
            throw new IllegalArgumentException("position cant have negative vars");*/
        this.depth=depth;
        this.row = row;
        this.column = column;
    }
    /**
     * Returns the depth index of the position
     * @return the depth index of the position
     */
    public int getDepthIndex() {return depth;}
    /**
     * Returns the row index of the position
     * @return the row index of the position
     */
    public int getRowIndex() {
        return row;
    }
    /**
     * Returns the column index of the position
     * @return the column index of the position
     */
    public int getColumnIndex() {
        return column;
    }
    /**
     * Check if the current position is equal to "other" - if it's the same position in the maze
     * @param other The object to check it's equality
     * @return true if it's the same position , otherwise - false
     */
    public boolean equals(Object other) {
        //null check??
        if (!(other instanceof Position3D))
            return false;
        return ((Position3D)other).depth==this.depth && ((Position3D)other).row==this.row && ((Position3D)other).column==this.column;
    }
    /**
     * Returns the hash code of the position
     * @return The hash code of the position
     */
    @Override
    public int hashCode() {
        return Objects.hash(row, column, depth);
    }

}
