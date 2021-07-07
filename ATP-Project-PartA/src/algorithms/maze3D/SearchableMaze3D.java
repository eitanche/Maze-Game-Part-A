package algorithms.maze3D;


import algorithms.search.AState;
import algorithms.search.AStateComperator;
import algorithms.search.ISearchable;

import java.util.ArrayList;
import java.util.Comparator;
/**
 * represent searchable 3D maze that can be solved by ASearchingAlgorithm
 */
public class SearchableMaze3D implements ISearchable {

    private Maze3DState startState;
    private Maze3DState goalState;
    private Maze3D maze;

    /**
     * constructor
     * @param maze represents a 3D Maze
     */
    public SearchableMaze3D(Maze3D maze) {
        if (maze == null)
            throw new NullPointerException("maze is null");
        int[][][] map = maze.getMap();
        if (map == null)
            throw new NullPointerException("Mull maze map");
        this.maze=maze;
        startState=new Maze3DState(maze.getStartPosition(),0);
        goalState= new Maze3DState(maze.getGoalPosition(),-1);

    }

    /**
     * checks if an Position in the maze is movable
     * @param move position that we want move to
     * @return true if move is valid position
     */
    private boolean checkPositionMovable(Position3D move) {
        return maze.PositionInMaze(move) && maze.getPositionValue(move)==0;// && !visited[move.getRowIndex()][move.getColumnIndex()].isVisited();
    }
    /**
     * add straight states to the successors list
     * @param depthInc incrementation of the depths
     * @param rowInc incrementation of the rows
     * @param colInc incrementation of the columns
     * @param lst list to add the states to
     * @param curr The state to find it's successors
     */
    private void addStraightState(int depthInc,int rowInc, int colInc, ArrayList<AState> lst, AState curr) {
        Maze3DState currentState = (Maze3DState) curr;
        Position3D curPos=currentState.getPosition();
        Position3D move=new Position3D(curPos.getDepthIndex()+depthInc,curPos.getRowIndex()+rowInc,curPos.getColumnIndex()+colInc);
        if (checkPositionMovable(move)) {
            Maze3DState state = new Maze3DState(move,currentState.getPositionValue()+10);
            state.setFather(currentState);
            lst.add(state);
        }
    }
    /**
     * returns AState comparator
     * @return AState comparator
     */
    public Comparator<AState> getComperator() {
        return new AStateComperator();
    }
    /**
     * returns successors list of a given state
     * @param curr represent a general picture of specific move in
     *      *      *            searchable problem
     * @return successors list of a given state
     */
    public ArrayList<AState> getAllSuccessors(AState curr) {
        ArrayList<AState> states= new ArrayList<AState>();
        addStraightState(0,0,1,states,curr);
        addStraightState(0,0,-1,states,curr);
        addStraightState(0,1,0,states,curr);
        addStraightState(0,-1,0,states,curr);
        addStraightState(1,0,0,states,curr);
        addStraightState(-1,0,0,states,curr);
        return states;
    }
    /**
     * @return goal state for searching algorithm
     */
    @Override
    public Maze3DState getEnd() {
        return goalState;
    }
    /**
     * @return first state for searching algorithm
     */
    @Override
    public Maze3DState getstart() {
        return startState;
    }
}
