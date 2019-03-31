package algorithms.search;

import java.util.Queue;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {
    private int numOfNodeEvaluated;
    protected Queue<AState> queue;

    public ASearchingAlgorithm(String name) {
        this.name = name;
        this.numOfNodeEvaluated = 0;
    }

    private String name;

    @Override
    public int getNumberOfNodesEvaluated() {
        return numOfNodeEvaluated;
    }

    protected void setNumOfNodeEvaluated(int numOfNodeEvaluated) {
        this.numOfNodeEvaluated = numOfNodeEvaluated;
    }

    public String getName() {
        return name;
    }
}
