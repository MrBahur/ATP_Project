package algorithms.search;

import java.util.ArrayList;
import java.util.LinkedList;

public class Solution {

    AState lastState;

    public Solution(AState lastState) {
        this.lastState = lastState;
    }

    public ArrayList<AState> getSolutionPath() {
        if (lastState == null) {
            return null; //no solution to the maze
        } else { //recursive finding the solution using linkedList
            LinkedList<AState> listOfState = new LinkedList<>();
            AState tmp = lastState;
            while (tmp != null) {
                listOfState.addFirst(tmp);
                tmp = tmp.getCameFrom();
            }
            return new ArrayList<AState>(listOfState);
        }
    }

}
