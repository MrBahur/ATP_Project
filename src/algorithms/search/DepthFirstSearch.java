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
            setVisited(nodesStatus, currentState);
            allSuccessors = domain.getAllSuccessors(currentState);

            for (AState s : allSuccessors) {
                if (!(isVisited(nodesStatus, s))) {
                    stack.push(s);
                    setVisited(nodesStatus, s);
                    s.setCameFrom(currentState);
                    s.setCost(currentState.getCost()+s.getCost());
                }
            }
        }
        return null;
    }

    private void setVisited(HashMap<AState, Boolean> nodesStatus, AState visitedState) {
        if (!nodesStatus.containsKey(visitedState)) {
            nodesStatus.put(visitedState, true);
        }
    }

    private boolean isVisited(HashMap<AState, Boolean> nodesStatus, AState stateToCheck) {
        return nodesStatus.containsKey(stateToCheck);
    }
}