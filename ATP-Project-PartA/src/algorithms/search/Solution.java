package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * represents a solution of searchable problem
 */
public class Solution implements Serializable {
    private ArrayList<AState> path;
    /**
     * constructor
     * @param sol an array list of solution path
     */
    public Solution(ArrayList<AState> sol) {
        if (sol == null)
            throw new NullPointerException("sol List is null");
        path=sol;
    }
    /**
     * @return's the path of the solution
     */
    public ArrayList<AState> getSolutionPath() {
        return path;
    }
}
