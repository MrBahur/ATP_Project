package algorithms.search;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {
    private int numOfNodeEvaluated;

    public ASearchingAlgorithm(String name) {
        this.name = name;
        this.numOfNodeEvaluated = 0;
    }

    private String name;

    public String getName() {
        return name;
    }
}
