package algorithms.search;

import java.util.ArrayList;

public abstract class AState{

    @Override
    public abstract boolean equals(Object o);

    private String state;
    private double cost;
    private AState cameFrom;

    public AState(String state, double cost, AState cameFrom) {
        this.state = state;
        this.cost = cost;
        this.cameFrom = cameFrom;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public void setCameFrom(AState cameFrom) {
        this.cameFrom = cameFrom;
    }
}


