package algorithms.search;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {

    @Override
    public int getNumberOfNodesEvaluated() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Solution solve(ISearchable domain) {
        return null;
    }
}
