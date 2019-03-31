package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;

public class SearchableMaze implements ISearchable {

    private Maze maze;

    public SearchableMaze(Maze maze) {
        this.maze = maze;
    }

    @Override
    public AState getStartState() {
        return new MazeState(maze, maze.getStartPosition());
    }

    @Override
    public AState getGoalState() {
        return new MazeState(maze, maze.getGoalPosition());
    }

    @Override
    public ArrayList<AState> getAllSuccessors(AState s) {

        return null;
    }
}