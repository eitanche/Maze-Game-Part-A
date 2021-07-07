package algorithms.mazeGenerators;

import java.util.Arrays;

/**
 * This class generates an empty maze without any walls
 */
public class EmptyMazeGenerator extends AMazeGenerator {


    /**
     * Generates a rowXcol maze
     * @param row The number of rows in the maze
     * @param col The number of cols in the maze
     * @return The new Maze generated
     */
    @Override
    public Maze generate(int row, int col) {
        Maze maze = null;
        if (row < 2 || col < 2 ){
            throw new IllegalArgumentException(String.format("cannot generate %d %d maze",row,col));
        }
        else{
            maze = new Maze(row,col);
            InitBoard(maze,0);
            //start position is middle of first row
            Position start = new Position(row/2,0);
            //start position is middle of last row
            Position end = new Position(row/2,col-1);
            maze.setStart(start);
            maze.setGoal(end);
        }
        return maze;
    }
}
