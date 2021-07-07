package Server;

import algorithms.search.BestFirstSearch;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.DepthFirstSearch;
import algorithms.search.ISearchingAlgorithm;

/**
 * This class is a factory which creates SearchingAlgorithm object.
 * Used for the server according to the config file
 */
public class SearchingAlogrithmFactory {
    /**
     * Creates a searching algorithm object according to the property in the config file.
     * @return
     */
    public static ISearchingAlgorithm getSearchingAlgorithm(){
         switch (Configurations.getConfigInstance().loadProp().getProperty("mazeSearchingAlgorithm")){
                    case "BreathFirstSearch":
                        return new BreadthFirstSearch();
                    case "DepthFirstSearch":
                        return new DepthFirstSearch();
                    default:
                        return new BestFirstSearch();
                }
    }
}
