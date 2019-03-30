package algorithms.search;

import algorithms.mazeGenerators.Maze;

import java.util.ArrayList;

public interface ISearchable {

    AState getStartState();

    AState getGoalState();

    ArrayList<AState> getAllSuccessors(AState s);

}
