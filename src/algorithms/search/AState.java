package algorithms.search;

public abstract class AState {

    private String state;
    private double cost;
    private AState cameFrom;

    public AState(String state) {
        this.state = state;
    }
}

