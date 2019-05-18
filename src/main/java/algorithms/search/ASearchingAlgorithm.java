package algorithms.search;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {

    private int numOfNodeEvaluated;

    /**
     * Constructor for ASearchingAlgorithm that only get it's name
     *
     * @param name the name of the searching algorithm
     */
    public ASearchingAlgorithm(String name) {
        this.name = name;
        this.numOfNodeEvaluated = 0;
    }

    private String name;

    //region Getters
    @Override
    public int getNumberOfNodesEvaluated() {
        return numOfNodeEvaluated;
    }

    public String getName() {
        return name;
    }
    //endregion

    //region Setters
    protected void setNumOfNodeEvaluated(int numOfNodeEvaluated) {
        this.numOfNodeEvaluated = numOfNodeEvaluated;
    }
    //endregion

}
