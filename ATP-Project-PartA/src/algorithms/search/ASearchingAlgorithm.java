package algorithms.search;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * represent a Searching algorithm which can solve searchable problems.
 * All the searching algorithms should extend this class.
 */
public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {

    protected long numberOfNodeEvaluated;
    protected HashSet<AState> visited;
    protected ISearchable s;
    protected AState goal;

    /**
     * Initializing variables
     */
    public ASearchingAlgorithm() {
        visited = new HashSet<>();
        numberOfNodeEvaluated=0;
    }

    /**
     * mark state as a state we already visited, by adding it to the visited set.
     * @param state represent a general picture of specific move in
     *              searchable problem
     */
    protected void visit(AState state){
        if (state == null)
            throw new NullPointerException("state is null");

        visited.add(state);
    }
    /**
     * check if we already visited in this current state
     * @param state represent a general picture of specific move in
     *      *              searchable problem
     * @return true if visited and false if not
     */
    protected boolean isVisit(AState state){
        if (state == null)
            throw new NullPointerException("state is null");
        return visited.contains(state);
    }

    /**
     * checks if the state that given is the state that the algorithm is searching
     * @param state represent a general picture of specific move in
     *      *      *              searchable problem
     * @return true if the state that given is the state that the algorithm is searching else return false
     */
    protected boolean isSolved(AState state){
        if (state == null)
            throw new NullPointerException("state is null");
        return state.equals(s.getEnd());
    }

    /** return an ArrayList of states with the solution path
     * @param goal represent the state that the algorithm is searching
     * @return an ArrayList of states with the solution path
     */
    protected ArrayList<AState> getSolution(AState goal){
        if (goal == null)
            throw new NullPointerException("Maze Without Solution");
        ArrayList<AState> lst = new ArrayList<AState>();
        lst.add(goal);
        if (goal.getFather() == null)
            throw new IllegalArgumentException("Maze Without Solution");
        while (goal.getFather() != null){
            lst.add(goal.getFather());
            goal=goal.getFather();
        }
        Collections.reverse(lst);
        //s.clear();
        return lst;
    }
    /**
     * return's the number of nodes that visited by the algorithm
     * @return integer represents this number
     */
    @Override
    public long getNumberOfNodesEvaluated() {
        return numberOfNodeEvaluated;
    }

}
