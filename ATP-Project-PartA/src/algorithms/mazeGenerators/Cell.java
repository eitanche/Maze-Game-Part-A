package algorithms.mazeGenerators;

import java.util.LinkedList;

/**
 * This class is used for a maze generation (in MyMazeGenerator class).
 * It represents a cells position, and a list of its possible neighbours in the maze
 */
public class Cell {
    private Position position;
    private LinkedList<Position> neighbours;

    /**
     * Creates a new Cell object.
     * @param position the position of the cell
     * @param neighbours a list of the cell's neighbours
     */
    public Cell(Position position, LinkedList<Position> neighbours) {
        if (position == null || neighbours == null)
            throw new NullPointerException("position or LinkedList is null");
        this.position = position;
        this.neighbours = neighbours;
    }

    /**
     * Checks if the cell has possible neighbours
     * @return true if the cell has possible neighbours, otherwise - false
     */
    public boolean  HaveNeighbours(){
        if (neighbours.isEmpty())
            return false;

        return !(neighbours.isEmpty()) ;

    }

    /**
     * the Cell's position
     * @return the Cell's position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * If the cell has a possible neighbour - remove it from the list and return it.
     * @return A possible neighbour of the cell, or null if there's no neighbour
     */
    public Position GetCellNeighbour(){
        if (this.neighbours.isEmpty()){
            return null;
        }
        return this.neighbours.pop();
    }
}
