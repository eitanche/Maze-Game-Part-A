package algorithms.search;

import java.util.*;

/**
 * this class implements a breadth First Search algorithm by
 * using queue for any searchable problem
 *
 * breadth first search finding the shortest path to the goal state
 */
public class BreadthFirstSearch extends ASearchingAlgorithm
{

    protected ArrayList<AState> Alist;
    protected Object queue;
    protected AState curr;

    /**
     * constructor
     */
    public BreadthFirstSearch() {
        queue = new LinkedList<AState>();
        Alist = new ArrayList<>();
    }
    /**
     * returns the shortest solution path
     * @param s A searchable problem
     * @return path of the shortest solution
     */

    @Override
    public Solution solve(ISearchable s) {
        if (s == null)
            throw new NullPointerException("Searchable is null");
        this.s=s;
        goal = BFS();
      //  return null;
        return (new Solution(getSolution(goal)));
    }

    /**
     * if the algorithm didn't visit the given state, visit him and add him to the queue
     * @param state represent a general picture of specific move in
     *      *      *      *              searchable problem
     */
    protected void enqueue(AState state) {
        if (state == null)
            throw new NullPointerException("state is null");
        if (!isVisit(state) ){
            visit(state);
            numberOfNodeEvaluated++;
            ((LinkedList<AState>) queue).add(state);
        }
    }
    /**
     * implements BFS
     * @return goal state with the shortest path
     */
    protected AState BFS(){
        curr = s.getstart();
        numberOfNodeEvaluated++;
        enqueue(curr);
        while (!isEmpty()){
            curr = dequeue();
            if (isSolved(curr))
                return curr;
            Alist = s.getAllSuccessors(curr);
            for (AState state: Alist ) {
                enqueue(state);
            }
        }
        return null;
    }

    /**
     * return's the state in the head of the queue
     * @return the state in the head of the queue
     */
    protected AState dequeue(){
        return ((LinkedList<AState>)queue).poll();
    }

    /**
     * checks if queue that implement by Linked list is empty
     * @return true if queue is empty and false if not
     */
    protected boolean isEmpty(){
        boolean res = (((Queue<AState>)queue).isEmpty());
        return res;
    }

    /**
     * return the name of the searching algorithm
     * @return the name of the searching algorithm
     */
    @Override
    public String getName() {
        return "Breadth First Search";
    }
}

