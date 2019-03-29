package algorithms.search;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm{
    @Override
    public AState search(ISearchable e) {
        return null;
    }

    @Override
    public int getNumberOfVisitedNodes() {
        return 0;
    }
}
