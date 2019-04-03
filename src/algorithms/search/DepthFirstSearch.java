package algorithms.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class DepthFirstSearch extends ASearchingAlgorithm {
    private HashMap<AState, Boolean> nodesStatus;
    private Stack<AState> stack;

    public DepthFirstSearch() {
        super("Depth First Search");
        nodesStatus = new HashMap<>(); // false - not yet visited, true - visited
        stack = new Stack<>();
    }

    public Solution solve(ISearchable domain) {
        if (domain == null) {
            throw new NullPointerException();
        }

        AState startState = domain.getStartState();
        AState goalState = domain.getGoalState();
        AState currentState;
        ArrayList<AState> allSuccessors;
        stack.push(startState);
        while (!(stack.empty())) {
            currentState = stack.pop();
            setNumOfNodeEvaluated(getNumberOfNodesEvaluated() + 1);
            if (currentState.equals(goalState)) {
                return new Solution(currentState);
            }
            if(!(nodesStatus.containsKey(currentState)))
                nodesStatus.put(currentState, true);
            allSuccessors = domain.getAllSuccessors(currentState);

            for (AState currentSuccessor : allSuccessors) {
                if (!(nodesStatus.containsKey(currentSuccessor))) {
                    stack.push(currentSuccessor);
                    nodesStatus.put(currentSuccessor, true);
                    currentSuccessor.setCameFrom(currentState);
                    currentSuccessor.setCost(currentState.getCost()+currentSuccessor.getCost());
                }
            }
        }
        return null;
    }
}