package algorithms.mazeGenerators;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a position in the maze
 */
public class Position implements Serializable {
    private int row;
    private int column;

    /**
     * creates a new position object
     * @param row the row index of the position
     * @param column the col index of the position
     */
    public Position(int row, int column) {
    //    if (row <0 || column < 0) cause problems
     //       throw new IndexOutOfBoundsException("cannot have negative rows or columns");
        this.row = row;
        this.column = column;
    }

    /**
     * Returns the row index of the position
     * @return the row index of the position
     */
    public int getRowIndex() {
        return row;
    }
    /**
     * Returns the col index of the position
     * @return the col index of the position
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
        if (!(other instanceof Position))
            return false;
        return ((Position)other).row==this.row && ((Position)other).column==this.column;
    }

    /**
     * Returns the hash code of the position
     * @return The hash code of the position
     */
    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    /**
     * Returns The position which is in the middle between current position
     * and the position which is received
     * @param p The position which is the middle position is calculated with
     * @return The position which is in the middle between current position
     * and the position which is received
     */
    public Position getBetween(Position p){
        if (p == null)
            throw new NullPointerException("Position is null");
        return new Position((p.getRowIndex()-row)/2+row,(p.getColumnIndex()-column)/2+column);
    }
}

