package algorithms.mazeGenerators;

import java.util.Random;
public class SimpleMazeGenerator extends AMazeGenerator {

    @Override
    /**
     * Generates a rowXcol  simple maze
     * @param row The number of rows in the maze
     * @param col The number of cols in the maze
     * @return The new Maze generated
     */
    public Maze generate(int row, int col) {
        if (row < 2 && col < 2 ){
            throw new IllegalArgumentException(String.format("cannot generate %d %d maze",row,col));
        }
        Maze maze = new Maze(row,col);
        InitBoard(maze,2);
        createRoad(maze);
        randomCells(maze);
        return maze;
    }

    /**
     * Creates the maze's start and end positions, and a road between them
     * @param maze
     */
    private void createRoad(Maze maze) {
        //Start position is at the 1st row, and 1/4 col
        Position start = new Position(0,maze.getCols()/4);
        //end position is at the last row, and 3/4 col
        Position end= new Position(maze.getRows()-1,maze.getCols()/4*3);
        maze.setStart(start);
        maze.setGoal(end);
        int[][] map = maze.getMap();
        int i=0, j = maze.getCols()/4;
        map[i][j]=0;
        //the loop creates the road from start to end, by going one step down and than one step right
        //(if it doesn't cross the end position indexes)
        while (i!=maze.getRows()-1 || j!=maze.getCols()/4*3) {
            if (i<maze.getRows()-1) {
                i++;
                map[i][j]=0;
            }
            if (j<maze.getCols()/4*3) {
                j++;
                map[i][j]=0;
            }
        }
    }

    /**
     * for every cell which isn't on the road from start to end, randomize it's value (wall or passage)
     * @param maze the maze to randomize its cells
     */
    private void randomCells(Maze maze) {
        Random rand=new Random();
        int[][] map = maze.getMap();
        for(int i=0;i<map.length;i++) {
            for (int j=0;j<map[0].length;j++)
                if (map[i][j]==2)
                    map[i][j]=rand.nextInt(2);
        }
    }
}
