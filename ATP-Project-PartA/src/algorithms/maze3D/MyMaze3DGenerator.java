package algorithms.maze3D;


import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

/**
 * This class represents a 3D Maze generator - a class which creates 3D mazes using DFS algorithm
 */
public class MyMaze3DGenerator extends AMaze3DGenerator{

    private Random rnd;

    /**
     * Constructor
     */
    public MyMaze3DGenerator() {
        rnd=new Random();
    }

    /**
     * This method gets a 3D maze, and returns a random position at one of the maze's edges
     * @param maze
     * @return
     */
    private Position3D randomEdge(Maze3D maze) {
        if (maze == null)
            throw new NullPointerException("maze arg is null");
        int edge = rnd.nextInt(6);
        int depth,row,col;
        if (edge<=1) { //inner or outer edge
            col=rnd.nextInt(maze.getCols());
            depth=rnd.nextInt(maze.getDepth());
            if (edge==0)  //inner edge
                row=0;
            else //outer edge
                row= maze.getRows()-1;
        }
        else if (edge <=3){ //Left or Right edge
            row=rnd.nextInt(maze.getRows());
            depth=rnd.nextInt(maze.getDepth());
            if (edge==2) //left edge
                col=0;
            else //right edge
                col=maze.getCols()-1;
        }
        else { //Top or Bottom edge
            row=rnd.nextInt(maze.getRows());
            col=rnd.nextInt(maze.getCols());
            if (edge==4) //top edge
                depth=0;
            else //bottom edge
                depth=maze.getDepth()-1;
        }
        return new Position3D(depth,row,col);
    }

    /**
     * Generates a 3D Maze with specific sizes - must be greater than 1
     * @param depth The number of depths in the maze
     * @param row The number of rows in the maze
     * @param col The number of cols in the maze
     * @return A Maze3D object
     */
    @Override
    public Maze3D generate(int depth,int row, int col) {
        if (depth < 1 || row < 1 || col < 1)
            throw new IllegalArgumentException("cant make 3D maze with this vars");

        if (depth == 2 && row== 2 && col == 2)
            return twoOnTwoMaze();
        Maze3D maze = new Maze3D(depth,row,col);
        InitBoard(maze,1);
        Position3D start;
        //Generate a random start position, until it has a possible neighbour (with a distance of 2)
        do {
            start = randomEdge(maze);
        }
        while ((GetMyNeibs(maze.getMap(),new LinkedList<Integer>(), start) == null));
        maze.setPositionValue(start,0);
        maze.setStart(start);
        //The stack which is used for the DFS algorithm
        Stack<Position3D> stack = new Stack<Position3D>();
        stack.push(start);
        DFS(maze,stack);
        setGoal(maze);
        return maze;
    }

    /**
     * Creates a 2X2X2 3D Maze
     * @param
     * @return A 2X2X2 3D Maze
     */
    private Maze3D twoOnTwoMaze() {
        Maze3D maze = new Maze3D(2,2,2);
        int [][][] map = maze.getMap();
        map [0][0][0] = 1;
        map [0][0][1] = 0;
        map [0][1][0] = 0;
        map [0][1][1] = 0;
        map [1][0][0] = 1;
        map [1][0][1] = 0;
        map [1][1][0] = 1;
        map [1][1][1] = 1;
        maze.setGoal(new Position3D(1,0,1));
        maze.setStart(new Position3D(0,1,1));
        return maze;
    }

    /**
     * Sets the maze's goal end, so it will be on the edge of the Maze, and with some distance from the start point
     * @param maze
     */
    private void setGoal(Maze3D maze) {
        if (maze == null)
            throw new NullPointerException("maze arg is null");
        Position3D p;
        //the minimum distance from the start position
        int okGoal = (maze.getDepth()+maze.getCols()+maze.getRows())/6;
        int minPos;
        do {
            p=randomEdge(maze);
            //The distance of the random position from the start
            minPos = (Math.abs(maze.getStartPosition().getDepthIndex()-p.getDepthIndex())
                    +Math.abs(maze.getStartPosition().getColumnIndex()-p.getColumnIndex()) +
                    Math.abs(maze.getStartPosition().getRowIndex()-p.getRowIndex()));
        }
        //generate a new end position until we get a position which is reachable, and far enough from the start
        while (maze.getPositionValue(p)!=0 || p.equals(maze.getStartPosition())||  minPos < okGoal );
        maze.setGoal(p);
    }

    /**
     * The main part of the maze generation - The DFS algorithm
     * @param maze The maze to change it's map
     * @param stack The stack which is used for the algorithm
     */
    private void DFS(Maze3D maze, Stack<Position3D> stack){
        if (maze == null || stack == null)
            throw new NullPointerException("some arg is null");
        int [][][] map = maze.getMap();
        if (map == null)
                throw new NullPointerException("maze map is null");
        //The indexes of the current position, and of its neighbour (posD,posR,posC)
        int posD,posR,posC,curD,curR,curC;
        //A list which is used to find a random neighbour of the current position - for GetMyNeibs method
        LinkedList<Integer> neighbour = new LinkedList<Integer>();
        while(!stack.isEmpty()){
            Position3D currentCell = stack.pop();
            curD=currentCell.getDepthIndex();
            curC=currentCell.getColumnIndex();
            curR=currentCell.getRowIndex();
            //find a random neighbour of the current position
            Position3D neib = GetMyNeibs(map,neighbour,currentCell);
            if (neib==null)
                continue;
            //return the current position to the stack, break the wall between the position and it's neighbour
            //and than add the neighbour to the stack
            stack.push(currentCell);
            posD=neib.getDepthIndex();
            posR=neib.getRowIndex();
            posC=neib.getColumnIndex();
            map[posD][posR][posC] = 0;
            map[(posD-curD)/2+curD][(posR-curR)/2+curR][(posC-curC)/2+curC]=0;
            stack.push(neib);
        }
    }

    /**
     * Returns a random possible neighbour of the position - only if it wasn't visited yet
     * @param map The map of the maze in generation
     * @param neibs A list of integers used for finding a neighbour
     * @param position The position to find it's neighbour
     * @return A random neighbour of position. Null if no such neighbour exists
     */
    private Position3D GetMyNeibs(int[][][] map ,LinkedList<Integer> neibs, Position3D position){
        if (position == null)
            return null;
        int posD,posR,posC;
        posD=position.getDepthIndex();
        posR=position.getRowIndex();
        posC=position.getColumnIndex();
        neibs.clear();
        //for every neighbour of the position, check if it's inside the maze and if it wasn't visited yet
        //If so- add it the number of the option to the list of the possible neighbour
        if (posD+2<map.length && map[posD+2][posR][posC]==1)
            neibs.add(1);
        if (posD-2>=0 && map[posD-2][posR][posC]==1)
            neibs.add(2);
        if (posR+2<map[0].length  && map[posD][posR+2][posC]==1)
            neibs.add(3);
        if ( posR-2>=0 && map[posD][posR-2][posC]==1)
            neibs.add(4);
        if ( posC+2 <map[0][0].length && map[posD][posR][posC+2]==1)
            neibs.add(5); 
        if (posC-2 >=0 && map[posD][posR][posC-2]==1)
            neibs.add(6);
        //no possible neighbours
        if (neibs.size()==0)
            return null;
        //get a random number from the list of possible neighbour
        int choice = neibs.get(rnd.nextInt(neibs.size()));
        //create and return a position according to the random number from the list
        switch (choice) {
            case 1:
                return (new Position3D(posD+2,posR,posC));
            case 2:
                return (new Position3D(posD-2,posR,posC));
            case 3:
                return (new Position3D(posD,posR+2,posC));
            case 4:
                return (new Position3D(posD,posR-2,posC));
            case 5:
                return (new Position3D(posD,posR,posC+2));
            default:
                return (new Position3D(posD,posR,posC-2));
        }
    }
}