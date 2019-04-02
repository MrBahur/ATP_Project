package algorithms.search;

import algorithms.mazeGenerators.Position;

import java.util.*;

public class BreadthFirstSearch extends ASearchingAlgorithm {

    private HashMap<AState, Integer> colour;// not in or 0 = White, 1 = Grey, 2 = Black
    protected Queue<AState> queue;

    public BreadthFirstSearch(String name){
        super(name);
        colour = new HashMap<>();
    }
    public BreadthFirstSearch() {
        this("Breadth First Search");
        queue = new LinkedList<>();
    }

    @Override
    public Solution solve(ISearchable domain) {
        if (domain == null) {
            throw new NullPointerException();
        }
        AState start = domain.getStartState();
        AState goal = domain.getGoalState();
        colour.put(start, 1);
        queue.add(start);
        colour.put(goal, 0);
        while (!queue.isEmpty()) {
            AState currentState = queue.remove();
            ArrayList<AState> currentStateNeighbours = domain.getAllSuccessors(currentState);
            if(currentState.hashCode()==goal.hashCode()&&currentState.equals(goal)){
                return new Solution(currentState);
            }
            for (AState s : currentStateNeighbours) {
                boolean isWhite = false;
                if (!colour.containsKey(s)) {
                    isWhite = true;
                } else if (colour.get(s) == 0) {
                    colour.remove(s);
                    isWhite = true;
                }
                if (isWhite) {
                    colour.put(s, 1);
                    s.setCameFrom(currentState);
                    s.setCost(currentState.getCost() + s.getCost());
                    queue.add(s);
                    setNumOfNodeEvaluated(getNumberOfNodesEvaluated() + 1);
                }
            }
            colour.remove(currentState);
            colour.put(currentState, 2);
        }
        return null;
    }
}
