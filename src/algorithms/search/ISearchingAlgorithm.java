package algorithms.search;

public interface ISearchingAlgorithm {

    int getNumberOfNodesEvaluated();

    String getName();

    AState solve(ISearchable domain);
}
