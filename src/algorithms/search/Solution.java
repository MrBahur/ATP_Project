package algorithms.search;

import java.util.ArrayList;

public class Solution{

    AState solution;
    ArrayList<AState> solutionPath;

    public Solution(AState solution, ArrayList<AState> solutionPath) {
        this.solution = solution;
        this.solutionPath = solutionPath;
    }

    public ArrayList<AState> getSolutionPath() {
        return null;
    }

    public void setSolution(AState solution) {
        this.solution = solution;
    }
}
