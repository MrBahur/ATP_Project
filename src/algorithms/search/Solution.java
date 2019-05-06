package algorithms.search;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class Solution implements Serializable {

    private ArrayList<AState> solution;

    public Solution(AState lastState) {
        this.solution = new ArrayList<>();
        if (lastState != null) {
            LinkedList<AState> listOfState = new LinkedList<>();
            AState tmp = lastState;
            while (tmp != null) {
                listOfState.addFirst(tmp);
                tmp = tmp.getCameFrom();
            }
            solution.addAll(listOfState);
        }
    }

    public ArrayList<AState> getSolutionPath() {
        return this.solution;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        for (AState s : this.solution) {
            out.writeObject(s);
        }
    }

    private void readObject(ObjectInputStream in) {
        this.solution = new ArrayList<>();
        try {
            while (true) {
                AState s = (AState) in.readObject();
                this.solution.add(s);
            }
        } catch (IOException | ClassNotFoundException e) {
            //e.printStackTrace();
        }

    }

}



//package algorithms.search;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.LinkedList;
//
//public class Solution implements Serializable {
//
//    AState lastState;
//
//    public Solution(AState lastState) {
//        this.lastState = lastState;
//    }
//
//    public ArrayList<AState> getSolutionPath() {
//        if (lastState == null) {
//            return new ArrayList<>(); //no solution to the maze, empty array list
//        } else { //recursive finding the solution using linkedList
//            LinkedList<AState> listOfState = new LinkedList<>();
//            AState tmp = lastState;
//            while (tmp != null) {
//                listOfState.addFirst(tmp);
//                tmp = tmp.getCameFrom();
//            }
//            return new ArrayList<>(listOfState);
//        }
//    }
//
//}
