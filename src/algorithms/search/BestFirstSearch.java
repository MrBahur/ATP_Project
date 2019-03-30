package algorithms.search;

import java.util.ArrayList;

public class BestFirstSearch extends ASearchingAlgorithm{


    @Override
    public AState solve(ISearchable domain) {
        AState startState = domain.getStartState();
        AState goalState = domain.getGoalState();
        ArrayList<AState> successors = domain.getAllSuccessors(startState);









        return null;
    }
}
