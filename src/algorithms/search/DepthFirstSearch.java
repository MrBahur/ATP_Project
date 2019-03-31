package algorithms.search;

import algorithms.mazeGenerators.Position;

import java.util.ArrayList;

public class DepthFirstSearch extends ASearchingAlgorithm {

    @Override
    public int getNumberOfNodesEvaluated() {
        return 0;
    }

    public Solution solve(ISearchable domain) {

        AState startState = domain.getStartState();
        AState goalState = domain.getGoalState();
        ArrayList<AState> solutionPath = new ArrayList<>();
        solutionPath.add(startState);
        Solution solution = new Solution(null, solutionPath);
        if (isSolved(domain, solutionPath, (MazeState) goalState)) {
            AState goal = solutionPath.get(solutionPath.size() - 1);
            solution.setSolution(goal);
            return solution;
        }
        return null;
    }

    private boolean isSolved(ISearchable domain, ArrayList<AState> currentPath, MazeState goalState){

        MazeState currentState = (MazeState) currentPath.get(currentPath.size() - 1);
        if (currentState.equals(goalState)) {
            return true;
        }
        ArrayList<AState> currentSuccessors = domain.getAllSuccessors(currentState);
        if (domain.getAllSuccessors(currentState).size() == 0) {
            currentPath.remove(currentPath.size() - 1);
            return false;
        }
        MazeState nextSuccessor = (MazeState) currentSuccessors.get(0);
        currentPath.add(nextSuccessor);
        return isSolved(domain, currentPath, goalState);
    }
}