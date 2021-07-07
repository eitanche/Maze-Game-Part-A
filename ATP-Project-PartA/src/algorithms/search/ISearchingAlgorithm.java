package algorithms.search;

/**
 * This interface represents a general searching algorithm
 */
public interface ISearchingAlgorithm {
    /**
     * Gets a searchable problem, solves it and returns the solution
     * @param s The problem to solve
     * @return The solution to the problem
     */
    public Solution solve(ISearchable s);

    /**
     * Returns the algorithm's name
     * @return the algorithm's name
     */
    public String getName();

    /**
     * Returns the number of nodes the algorithm evaluated
     * @return the number of nodes the algorithm evaluated
     */
    public long getNumberOfNodesEvaluated();
}
