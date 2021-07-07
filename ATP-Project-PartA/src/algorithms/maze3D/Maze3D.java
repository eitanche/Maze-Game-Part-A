package algorithms.maze3D;

/**
 * This class represents a 3D maze.
 */
public class Maze3D {
    private Position3D start;
    private Position3D goal;
    private int rows;
    private int cols;
    private int depth;
    private int[][][] map3D;
    /**
     * creates a new depthXrowsXcols maze object
     * @param depth the number of maze's depth's
     * @param rows the number of maze's rows
     * @param cols the number of maze's cols
     */
    public Maze3D(int depth,int rows, int cols) {
        if (depth < 1 || rows < 1 || cols < 1)
            throw new IllegalArgumentException("cant make 3D maze with this vars");
        this.rows = rows;
        this.cols = cols;
        this.depth = depth;
        this.map3D = new int[depth][rows][cols];
    }
    /**
     * Sets the mazes start position
     * @param start The maze's start position
     */
    public void setStart(Position3D start) {
        if (start == null)
            throw new NullPointerException("start pos arg is null");
        this.start = start;
    }
    /**
     * Sets the mazes goal position
     * @param goal The mazes goal position
     */
    public void setGoal(Position3D goal) {
        if (goal == null)
            throw new NullPointerException("Goal pos arg is null");
        this.goal = goal;
    }
    /**
     * Returns the mazes start position
     * @return the mazes start position
     */
    public Position3D getStartPosition() {
        return start;
    }
    /**
     * Returns the mazes goal position
     * @return the mazes goal position
     */
    public Position3D getGoalPosition() {
        return goal;
    }
    /**
     * Returns the maze depth size
     * @return the maze depth size
     */
    public int getDepth() { return depth; }
    /**
     * Returns the number of rows in the maze
     * @return the number of rows in the maze
     */
    public int getRows() {
        return rows;
    }
    /**
     * Returns the number of cols in the maze
     * @return the number of cols in the maze
     */
    public int getCols() {
        return cols;
    }

    /**
     * Returns the map of the maze, 1-for a wall and 0-for a passage
     * @return A 2D array which represents the maze.
     */
    public int[][][] getMap() {
        return map3D;
    }
    /**
     * Returns a char which represents a specific maze cell
     * @param depth The index of the cells depth
     * @param row The index of the cells row
     * @param col The index of the cells col
     * @return S-Start Position, E-End Position, 1-wall, 0-passage
     */
    private String getCellString(int depth,int row, int col) {
        if (start.getDepthIndex()==depth &&start.getRowIndex()==row && start.getColumnIndex()==col)
            return "S";
        else if (goal.getDepthIndex()==depth && goal.getRowIndex()==row && goal.getColumnIndex()==col)
            return "E";
        else
            return map3D[depth][row][col]+"";
    }
    /**
     * Prints the maze
     */
    public void print(){
        System.out.println("{");
        for(int depth = 0; depth < map3D.length; depth++){
            for(int row = 0; row < map3D[0].length; row++) {
                System.out.print("{ ");
                for (int col = 0; col < map3D[0][0].length; col++) {
                    if (depth == start.getDepthIndex() && row == start.getRowIndex() && col == start.getColumnIndex()) // if the position is the start - mark with S
                        System.out.print("S ");
                    else {
                        if (depth == goal.getDepthIndex() && row == goal.getRowIndex() && col == goal.getColumnIndex()) // if the position is the goal - mark with E
                            System.out.print("E ");
                        else
                            System.out.print(map3D[depth][row][col] + " ");
                    }
                }
                System.out.println("}");
            }
            if(depth < map3D.length - 1) {
                System.out.print("---");
                for (int i = 0; i < map3D[0][0].length; i++)
                    System.out.print("--");
                System.out.println();
            }
        }
        System.out.println("}");
    }
    /**
     * Sets a maze's position to a specific value
     * @param pos The position to change its value
     * @param num The new value. 1-wall, 0-passage
     */
    public void setPositionValue(Position3D pos, int num) {
        if (pos == null)
            throw new NullPointerException("pos arg is null");
        map3D[pos.getDepthIndex()][pos.getRowIndex()][pos.getColumnIndex()] = num;
    }
    /**
     * returns a maze's position value
     * @param pos The position to return its value
     * @return The positions value. 1-wall, 0-passage
     */
    public int getPositionValue(Position3D pos) {
        return map3D[pos.getDepthIndex()][pos.getRowIndex()][pos.getColumnIndex()];
    }


    /**
     * checks if the position is inside the maze
     * @param position the position to check
     * @return true if the position is inside the maze, otherwise - false
     */
    public boolean PositionInMaze(Position3D position) {
        if (position == null)
            throw new NullPointerException("position arg is null");
        return (position.getDepthIndex() >=0 && position.getDepthIndex() < depth && position.getColumnIndex() < cols && position.getRowIndex() < rows && position.getRowIndex() >= 0 && position.getColumnIndex() >= 0);
    }
}
