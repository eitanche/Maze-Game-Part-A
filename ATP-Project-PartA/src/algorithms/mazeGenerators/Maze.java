package algorithms.mazeGenerators;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * This class represents a maze.
 */
public class Maze implements Serializable {
    private Position start;
    private Position goal;
    private int rows;
    private int cols;
    private int[][] map;
 /*
    rows - 2 bytes
    cols -2 bytes
    start rows -2 bytes
    start cols - 2 bytes
    goal rows - 2 bytes
    goal cols - 2 bytes
    map starts with index 12
    starts with 1
    */

    /**
     * Receives a byte array which represents a maze, and constructs a Maze out of it.
     * @param bMaze The byte array of the maze
     */
    public Maze(byte [] bMaze){
        if (bMaze == null)
            throw new NullPointerException("Byte array is null");
        this.rows = ((bMaze[1])&0xFF)+(bMaze[0]&0xFF)*256;
        this.cols = (bMaze[3]&0xFF)+(bMaze[2]&0xFF)*256;
        this.start = new Position((bMaze[4]&0xFF)*256+(bMaze[5]&0xFF),(bMaze[6]&0xFF)*256+(bMaze[7]&0xFF));
        this.goal = new Position((bMaze[8]&0xFF)*256+(bMaze[9]&0xFF),(bMaze[10]&0xFF)*256+(bMaze[11]&0xFF));
        map=new int[rows][cols];
        for (int i = 0; i < rows ; i++) {
            for (int j = 0; j < cols; j++) {
                map[i][j] = bMaze[12+j+i*cols]; // cast to int if there is a problem
            }
        }
    }
    /**
     * creates a new rowsXcols maze object
     * @param rows the number of maze's rows
     * @param cols the number of maze's cols
     */
    public Maze(int rows, int cols) {
        if (rows < 2 && cols < 2 ){
            throw new IllegalArgumentException(String.format("cannot generate %d %d maze",rows,cols));
        }
        this.rows = rows;
        this.cols = cols;
        map = new int[rows][cols];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Maze maze = (Maze) o;
        if (rows != maze.rows || cols != maze.cols || !start.equals(maze.start) || !goal.equals(maze.goal))
            return false;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (map[i][j]!=maze.map[i][j])
                    return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(calcSum(),rows, cols,start,goal);
        return result;
    }

    private int calcSum() {
        int sum=0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sum+=map[i][j];
            }
        }
        return sum;
    }

    /**
     * Creates a byte array which represents the maze. The next indexes of the byte array represent:
     * 0-1: The maze number of rows in 256 base
     * 2-3: The maze number of cols in 256 base
     * 4-5: The maze start position row index in 256 base
     * 6-7: The maze start position col index in 256 base
     * 8-9: The maze end position row index in 256 base
     * 10-11: The maze end position col index in 256 base
     * 12-end: Each byte is a poisition of the maze
     * @return
     */
    public byte[] toByteArray(){
        byte[] bMaze = new byte[12+rows*cols];
        bMaze[0] = (byte) (rows/256);
        bMaze[1] = (byte) (rows%256);
        bMaze[2] = (byte) (cols/256);
        bMaze[3] = (byte) (cols%256);
        bMaze[4] = (byte) (start.getRowIndex()/256);
        bMaze[5] = (byte) (start.getRowIndex()%256);
        bMaze[6] = (byte) (start.getColumnIndex()/256);
        bMaze[7] = (byte) (start.getColumnIndex()%256);
        bMaze[8] = (byte) (goal.getRowIndex()/256);
        bMaze[9] = (byte) (goal.getRowIndex()%256);
        bMaze[10] = (byte) (goal.getColumnIndex()/256);
        bMaze[11] = (byte) (goal.getColumnIndex()%256);
        for (int i = 0; i < rows ; i++) {
            for (int j = 0; j < cols; j++) {
                bMaze[12+j+i*cols] = (byte) map[i][j];
            }
        }
        return bMaze;
    }

    /**
     * Sets the mazes start position
     * @param start The maze's start position
     */
    public void setStart(Position start) {
        if (start == null)
            throw new NullPointerException("start Position is null");
        if (!PositionInMaze(start))
            throw new IndexOutOfBoundsException("Start position out of maze");
        this.start = start;
    }

    /**
     * Sets the mazes goal position
     * @param goal The mazes goal position
     */
    public void setGoal(Position goal) {
        if (goal == null)
            throw new NullPointerException("Goal Position is null");
        if (!PositionInMaze(goal))
            throw new IndexOutOfBoundsException("Goal position out of maze");
        this.goal = goal;
    }

    /**
     * Returns the mazes start position
     * @return the mazes start position
     */
    public Position getStartPosition() {
        return start;
    }

    /**
     * Returns the mazes goal position
     * @return the mazes goal position
     */
    public Position getGoalPosition() {
        return goal;
    }

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
    public int[][] getMap() {
        return map;
    }

    /**
     * Returns a char which represents a specific maze cell
     * @param row The index of the cells row
     * @param col The index of the cells col
     * @return S-Start Position, E-End Position, 1-wall, 0-passage
     */
    private String getCellString(int row, int col) {
        if (start.getRowIndex()==row && start.getColumnIndex()==col)
            return "S";
        else if (goal.getRowIndex()==row && goal.getColumnIndex()==col)
            return "E";
        else
            return map[row][col]+"";
    }

    /**
     * Prints the maze
     */
    public void print() {
        for (int i=0;i<map.length;i++) {
            System.out.print("{ ");
            int j;
            for (j=0;j<map[0].length;j++) {
                System.out.print(getCellString(i,j)+" ");
            }
            System.out.println("}");
        }
    }

    /**
     * Sets a maze's position to a specific value
     * @param pos The position to change its value
     * @param num The new value. 1-wall, 0-passage
     */
    public void setPositionValue(Position pos, int num) {
        if (pos == null)
            throw new NullPointerException("Position is null");
        if (!PositionInMaze(pos))
            throw new IndexOutOfBoundsException("position out of maze");
        map[pos.getRowIndex()][pos.getColumnIndex()] = num;
    }

    /**
     * returns a maze's position value
     * @param pos The position to return its value
     * @return The positions value. 1-wall, 0-passage
     */
    public int getPositionValue(Position pos) {
        if (pos == null)
            throw new NullPointerException("Position is null");
        if (!PositionInMaze(pos))
            throw new IndexOutOfBoundsException("position out of maze");
        return map[pos.getRowIndex()][pos.getColumnIndex()];
    }

    /**
     * Sets a maze's position to a specific value
     * @param row The row index of the position
     * @param col The col index of the position
     * @param num The new value. 1-wall, 0-passage
     */
    public void setPositionValue(int row, int col, int num) {
        if (!PositionInMaze(row,col))
            throw new IndexOutOfBoundsException("position out of maze");
        map[row][col] = num;
    }

    /**
     * returns a maze's position value
     * @param row the row index of the position
     * @param col the col index of the position
     * @return The positions value. 1-wall, 0-passage
     */
    public int getPositionValue(int row, int col) {
        if (!PositionInMaze(row,col))
            throw new IndexOutOfBoundsException("position out of maze");
        return map[row][col];
    }

    /**
     * Checks if the position is movable for the generation algorithm - inside the maze and it's a wall
     * @param position The position to check
     * @return True if the position is movable, otherwise - false
     */
    public boolean IsValidMove(Position position) {
        if (PositionInMaze(position)) {
            return (map[position.getRowIndex()][position.getColumnIndex()] == 1);
        }
        return false;
    }

    /**
     * checks if the position is inside the maze
     * @param position the position to check
     * @return true if the position is inside the maze, otherwise - false
     */
    public boolean PositionInMaze(Position position) {
        if (position == null)
            throw new NullPointerException("Null Position");
        return (position.getColumnIndex() < cols && position.getRowIndex() < rows && position.getRowIndex() >= 0 && position.getColumnIndex() >= 0);
    }

    /**
     * checks if the position is inside the maze
     * @param row the row index of the position to check
     * @param col the col index of the position to check
     * @return true if the position is inside the maze, otherwise - false
     */
    public boolean PositionInMaze(int row, int col) {
        return (col < cols && row < rows && row >= 0 && col >= 0);
    }
}
