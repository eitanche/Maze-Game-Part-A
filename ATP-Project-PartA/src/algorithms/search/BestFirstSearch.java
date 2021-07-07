package algorithms.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
/**
 * this class implements a Best First Search algorithm by
 * using priority queue for any searchable problem
 *
 * Best first search finding the cheapest path to the goal state
 */
public class BestFirstSearch extends BreadthFirstSearch {

    private HashMap <AState,Integer> valueOfPos;

    /**
     * Constructor
     */
    public BestFirstSearch() {
        valueOfPos = new HashMap<>();
        Alist = new ArrayList<>();
        queue = new PriorityQueue<AState>();
    }
    /**
     * returns the cheapest solution path
     * @param s A searchable problem
     * @return path of the cheapest solution
     */
    @Override
    public Solution solve(ISearchable s) {
        if (s == null)
            throw new NullPointerException("Searchable is null");
        queue = new PriorityQueue<AState>(11,s.getComperator());
        return super.solve(s);
    }

    /**
     * try to reach or improve price of state
     *
     * @param state represent a general picture of specific move in
     *      *      *              searchable problem
     */
    protected void enqueue(AState state){
        if (state == null)
            throw new NullPointerException("state is null");
        if (!isVisit(state)){
            numberOfNodeEvaluated++;
            if (!valueOfPos.containsKey(state)) {
                ((PriorityQueue<AState>) queue).add(state);
                valueOfPos.put(state,state.getPositionValue());
            }
            else{
                int oldState = valueOfPos.get(state);
                if (state.getPositionValue() < oldState){
                    ((PriorityQueue<AState>) queue).remove(state);
                    ((PriorityQueue<AState>) queue).add(state);
                    valueOfPos.put(state,state.getPositionValue());
                }

            }
        }
    }

    /**
     * return's the highest priority state
     * @return  the highest priority state
     */
    protected AState dequeue(){
        AState state = ((PriorityQueue<AState>)queue).poll();
        visit(state);
        return state;
    }

    /**
     * checks if queue is empty
     * @return true if queue is empty and false if not
     */
    protected boolean isEmpty(){
        boolean res = (((PriorityQueue<AState>)queue).isEmpty());
        return res;
    }

    /**
     * return the name of the searching algorithm
     * @return the name of the searching algorithm
     */
    @Override
    public String getName() {
        return "Best First Search";
    }

}
