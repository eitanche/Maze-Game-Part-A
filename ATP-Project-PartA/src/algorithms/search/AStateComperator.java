package algorithms.search;

import java.util.Comparator;
/**
 * Astate comparator for Generics structures
 */
public class AStateComperator implements Comparator<AState> { ///// try to use abstract

    /**
     * compare between two states
     * @param o1 first state
     * @param o2 secound state
     * @return which state is bigger:
     *          1 for o1
     *          -1 for o2
     *          0 if equals
     */
    @Override
    public int compare(AState o1, AState o2) {
        if (o1 == null || o2 == null)
            throw new NullPointerException("null Astate");
        if (o1.getPositionValue() > o2.getPositionValue()) {
            return 1;
        } else if (o1.getPositionValue() < o2.getPositionValue())
            return -1;

        return 0;

    }
}
