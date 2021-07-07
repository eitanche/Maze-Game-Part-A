package algorithms.maze3D;

import java.util.Arrays;
/**
 * This class represents an abstract 3DmazeGenerator, which implements the interface
 * IMazeGenerator. All the 3D maze Generators should extend this class.
 */
public abstract class AMaze3DGenerator implements IMaze3DGenerator {
    /**
     * measures the time it takes to generate a rowXcol maze
     * @param depth the number of maze depth
     * @param row the number of maze rows
     * @param col the number of maze cols
     * @return the time it took to genereate in milliseconds
     * @throws Exception in case of illegel size of maze
     */
    public long measureAlgorithmTimeMillis (int depth,int row,int col){
        if (depth < 1 || row < 1 || col < 1)
            throw new IllegalArgumentException("cant make 3D maze with this vars");
        long time = System.currentTimeMillis();
        generate(depth,row, col);
        long TotalTime = System.currentTimeMillis() - time;
        return TotalTime;
    }
    /**
     * Inits all the maze's cells with the number received as parameter
     * @param maze3D The maze which it cells will be initiated
     * @param number The number to init all the cells
     */
    protected void InitBoard(Maze3D maze3D, int number){
        if (maze3D == null)
            throw new NullPointerException("maze arg is null");
        int [][][] map3D = maze3D.getMap();
        //this for loop fills the array with the given number.
        for (int [][] map : map3D) {
            for(int [] rows: map)
                Arrays.fill(rows, number);
        }

    }

}

