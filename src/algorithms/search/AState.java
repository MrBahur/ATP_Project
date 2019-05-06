package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;


public abstract class AState implements Serializable {

    private String state;
    private double cost;
    private AState cameFrom;

    /**
     * Constructor for AState
     *
     * @param state    representation of the state as a string
     * @param cost     the cost to get to this state
     * @param cameFrom the previous state that got us to this state
     */
    public AState(String state, double cost, AState cameFrom) {
        this.state = state;
        this.cost = cost;
        this.cameFrom = cameFrom;
    }

    //region Getters
    private String getState() {
        return state;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public AState getCameFrom() {
        return cameFrom;
    }
    //endregion

    //region Setters
    public void setCameFrom(AState cameFrom) {
        this.cameFrom = cameFrom;
    }
    //endregion

    /**
     * Overriding toString for AState
     *
     * @return string that represent state
     */
    @Override
    public String toString() {
        return getState();
    }

    /**
     * Overriding hashCode for Position
     *
     * @return the State hashCode
     */
    @Override
    public int hashCode() {
        return getState().hashCode();
    }

    /**
     * Overriding equals for AState
     *
     * @param o another State of the same searching problem
     * @return true if the state represent the same state in the search problem
     */
    @Override
    public abstract boolean equals(Object o);

}

