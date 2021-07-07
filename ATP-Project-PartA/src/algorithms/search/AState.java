package algorithms.search;

import java.io.Serializable;

/**
 * represent a general picture of specific move in a searchable problem
 */
public abstract class AState implements Serializable {

    protected AState father;
    protected int positionValue;

    /**
     * Sets the state's father state
     * @param other The father state of this state
     */
    public void setFather(AState other){
        father = other;
    }

    /**
     * Returns the current state value
     * @return the current state value
     */
    public int getPositionValue() {
        return positionValue;
    }

    /**
     * Returns the current state father state
     * @return the current state father state
     */
    public AState getFather() {
        return this.father;
    }
}
