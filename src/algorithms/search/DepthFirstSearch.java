package algorithms.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class DepthFirstSearch extends ASearchingAlgorithm {

    public DepthFirstSearch() {
        super("Depth First Search");
    }

    @Override

    public int getNumberOfNodesEvaluated() {
        return 0;
    }

    public Solution solve(ISearchable domain) {

        AState startState = domain.getStartState();
        AState goalState = domain.getGoalState();
        Stack<AState> stack = new Stack<>();
        AState currentState = startState;
        ArrayList<AState> allSuccessors;

        stack.push(startState);
        while (!(stack.empty())) {

            currentState = stack.pop();
            currentState.setVisited(true);
            allSuccessors = domain.getAllSuccessors(currentState);

            if (allSuccessors.isEmpty()) {
                continue;
            }

            AState nextSuccessor = allSuccessors.get(0);
            while (nextSuccessor.isVisited() && allSuccessors.size() > 1) {
                allSuccessors.remove(0);
                nextSuccessor = allSuccessors.get(0);
            }

            if (nextSuccessor.isVisited()) {
                continue;
            }

            nextSuccessor.setCameFrom(currentState);

            if (nextSuccessor.equals(goalState)) {
                return new Solution(nextSuccessor);
            }
            stack.push(currentState);
            stack.push(nextSuccessor);
        }

        return null;
    }

//    private boolean alreadyVisited(ArrayList<AState> visited, AState checkIfVisited) {
//        for (AState state : visited) {
//            if (state.equals(checkIfVisited))
//                return true;
//        }
//        return false;
//    }

//        AState startState = domain.getStartState();
//        AState goalState = domain.getGoalState();
//        ArrayList<AState> evaluatedStates = new ArrayList<>();
//        AState solutionState = isSolved(domain, startState, goalState, evaluatedStates);
//        if (solutionState != null) {
//            Solution solution = new Solution(solutionState);
//            return solution;
//        }
//        return null;
//    }
//
//    private AState isSolved(ISearchable domain, AState currentState, AState goalState,
//                            ArrayList<AState> evaluatedStates) {
//
//        evaluatedStates.add(currentState);
//        if (currentState.equals(goalState)) {
//            return currentState;
//        }
//
//        ArrayList<AState> currentSuccessors = domain.getAllSuccessors(currentState);
//        if (currentSuccessors.size() == 0) {
//            evaluatedStates.remove(evaluatedStates.size() - 1);
//            return null;
//        }
//
//        AState solutionState = null;
//        while (solutionState == null && currentSuccessors.size() > 0) {
//            AState successor = currentSuccessors.get(0);
//            if (isEvaluated(evaluatedStates, successor)) {
//                currentSuccessors.remove(0);
//                continue;
//            }
//            solutionState = isSolved(domain, successor, goalState, evaluatedStates);
//            if (solutionState != null) {
//                successor.setCameFrom(currentState);
//                return solutionState;
//            }
//            if (solutionState == null)
//                currentSuccessors.remove(0);
//        }
//        return null;
//    }
//
//    private boolean isEvaluated(ArrayList<AState> evaluatedStates, AState checkIfEvaluated) {
//        for (AState state : evaluatedStates) {
//            if (state.equals(checkIfEvaluated))
//                return true;
//        }
//        return false;
//    }


}
