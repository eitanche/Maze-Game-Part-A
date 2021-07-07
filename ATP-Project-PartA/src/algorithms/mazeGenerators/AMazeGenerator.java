package algorithms.mazeGenerators;

import java.util.Arrays;

/**
 * This class represents an abstract mazeGenerator, which implements the interface
 * IMazeGenerator. All the maze Generators should extend this class.
 */
public abstract class AMazeGenerator implements IMazeGenerator{

    /**
     * measures the time it takes to generate a rowXcol maze
     * @param row the number of maze rows
     * @param col the number of maze cols
     * @return the time it took to genereate in milliseconds
     * @throws Exception in case of illegel size of maze
     */
        public long measureAlgorithmTimeMillis (int row,int col) {
        long TotalTime = 0;
        if (row > 1 && col > 1 ){
            long time = System.currentTimeMillis();
            generate(row, col);
            TotalTime = System.currentTimeMillis() - time;
        }
        else
            throw new IllegalArgumentException("number not in range");

        return TotalTime;

    }

    /**
     * Inits all the maze's cells with the number received as parameter
     * @param maze The maze which it cells will be initiated
     * @param number The number to init all the cells
     */
    protected void InitBoard(Maze maze,int number){
        if (maze == null)
            throw new NullPointerException("maze null pointer");
        int [][] map = maze.getMap();
        //this for loop fills the array with zeros.
        for (int [] rows : map) {
            Arrays.fill(rows, number);
        }

    }

}
