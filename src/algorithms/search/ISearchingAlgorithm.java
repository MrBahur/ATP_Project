package algorithms.search;

public interface ISearchingAlgorithm {

    AState search(ISearchable e);

    int getNumberOfVisitedNodes();
}
