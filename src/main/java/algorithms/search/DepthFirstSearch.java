package algorithms.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class DepthFirstSearch extends ASearchingAlgorithm {

    private HashMap<AState, Boolean> visited;
    private Stack<AState> stack;

    //region Constructors
    public DepthFirstSearch() {
        super("Depth First Search");
        visited = new HashMap<>(); // false - not yet visited, true - visited
        stack = new Stack<>();
    }
    //endregion

    /**
     * The algorithm
     *
     * @param domain the problem
     * @return the solution
     */
    public Solution solve(ISearchable domain) {
        if (domain == null) {
            System.out.println("Wrong input");
            return null;
        }
        AState startState = domain.getStartState();
        AState goalState = domain.getGoalState();
        AState currentState;
        ArrayList<AState> allSuccessors;
        stack.push(startState);
        while (!(stack.empty())) {
            currentState = stack.pop();
            setNumOfNodeEvaluated(getNumberOfNodesEvaluated() + 1);
            if (currentState.equals(goalState)) { //reached end position
                return new Solution(currentState);
            }
            if (!(visited.containsKey(currentState)))
                visited.put(currentState, true);
            allSuccessors = domain.getAllSuccessors(currentState);

            for (AState currentSuccessor : allSuccessors) { //Add all successors that are not visited yet
                if (!(visited.containsKey(currentSuccessor))) {
                    stack.push(currentSuccessor);
                    visited.put(currentSuccessor, true);
                    currentSuccessor.setCameFrom(currentState);
                    currentSuccessor.setCost(currentState.getCost() + currentSuccessor.getCost());
                }
            }
        }
        return null;
    }
}