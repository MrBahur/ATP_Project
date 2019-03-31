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

        ArrayList<AState> allSuccessors = new ArrayList<>();
        MazeState currentState = (MazeState) s;
        Position currentPosition = currentState.getCurrentPosition();
        int currentPRow = currentPosition.getRowIndex();
        int currentPColumn = currentPosition.getColumnIndex();
        if (currentPRow - 2 >= 0 && ((MazeState) s).maze.getMaze()[currentPRow-2][currentPColumn]==0)
            //If the neighbor above the current Position is part of the maze path
            allSuccessors.add(new MazeState(currentState.maze, new Position(currentPRow - 2, currentPColumn)));
        if (currentPColumn + 2 < currentState.maze.getColumns() && ((MazeState) s).maze.getMaze()[currentPRow][currentPColumn+2]==0)
            //If the neighbor on the right of the current Position is part of the maze path
            allSuccessors.add(new MazeState(currentState.maze, new Position(currentPRow, currentPColumn + 2)));
        if (currentPRow + 2 < currentState.maze.getRows() && ((MazeState) s).maze.getMaze()[currentPRow+2][currentPColumn]==0)
            //If the neighbor beneath the current Position is part of the maze path
            allSuccessors.add(new MazeState(currentState.maze, new Position(currentPRow + 2, currentPColumn)));
        if (currentPColumn - 2 >= 0 && ((MazeState) s).maze.getMaze()[currentPRow][currentPColumn-2]==0)
            //If the neighbor on the left of the current Position is part of the maze path
            allSuccessors.add(new MazeState(currentState.maze, new Position(currentPRow, currentPColumn - 2)));

        return allSuccessors;
    }
}