package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;

public class SearchableMaze implements ISearchable {

    private Maze maze;
    private MazeState startState;
    private MazeState goalState;

    public SearchableMaze(Maze maze) {
        this.maze = maze;
        this.startState = new MazeState(maze, maze.getStartPosition(), 0, null);
        this.goalState = new MazeState(maze, maze.getGoalPosition(), Double.POSITIVE_INFINITY, null);
    }

    @Override
    public AState getStartState() {
        return this.startState;
    }

    @Override
    public AState getGoalState() {
        return this.goalState;
    }

    @Override
    public ArrayList<AState> getAllSuccessors(AState s) {
        if (!(s instanceof MazeState)) {
            throw new IllegalArgumentException("Expected Maze state");
        }

        MazeState m = (MazeState) s;
        if (this.maze != m.getMaze()) {
            throw new IllegalArgumentException("this is a maze state but not of this maze");
        }
        int rowIndex = m.getCurrentPosition().getRowIndex();
        int columnIndex = m.getCurrentPosition().getColumnIndex();
        ArrayList<AState> toReturn = new ArrayList<>();
        boolean canMoveUp = rowIndex - 1 >= 0 && m.getMaze().getMaze()[rowIndex - 1][columnIndex] == 0;
        boolean canMoveDown = rowIndex + 1 < m.getMaze().getRows() && m.getMaze().getMaze()[rowIndex + 1][columnIndex] == 0;
        boolean canMoveRight = columnIndex + 1 < m.getMaze().getColumns() && m.getMaze().getMaze()[rowIndex][columnIndex + 1] == 0;
        boolean canMoveLeft = columnIndex - 1 >= 0 && m.getMaze().getMaze()[rowIndex][columnIndex - 1] == 0;
        if (canMoveUp) {
            toReturn.add(new MazeState(maze, new Position(rowIndex - 1, columnIndex), Double.POSITIVE_INFINITY, m));
        }
        if (canMoveDown) {
            toReturn.add(new MazeState(maze, new Position(rowIndex + 1, columnIndex), Double.POSITIVE_INFINITY, m));
        }
        if (canMoveRight) {
            toReturn.add(new MazeState(maze, new Position(rowIndex, columnIndex + 1), Double.POSITIVE_INFINITY, m));
        }
        if (canMoveLeft) {
            toReturn.add(new MazeState(maze, new Position(rowIndex, columnIndex - 1), Double.POSITIVE_INFINITY, m));
        }
        if (canMoveUp && canMoveRight && m.getMaze().getMaze()[rowIndex - 1][columnIndex + 1] == 0) {
            toReturn.add(new MazeState(maze, new Position(rowIndex - 1, columnIndex + 1), Double.POSITIVE_INFINITY, m));
        }
        if (canMoveUp && canMoveLeft && m.getMaze().getMaze()[rowIndex - 1][columnIndex - 1] == 0) {
            toReturn.add(new MazeState(maze, new Position(rowIndex - 1, columnIndex - 1), Double.POSITIVE_INFINITY, m));
        }
        if (canMoveDown && canMoveRight && m.getMaze().getMaze()[rowIndex + 1][columnIndex + 1] == 0) {
            toReturn.add(new MazeState(maze, new Position(rowIndex + 1, columnIndex + 1), Double.POSITIVE_INFINITY, m));
        }
        if (canMoveDown && canMoveLeft && m.getMaze().getMaze()[rowIndex + 1][columnIndex - 1] == 0) {
            toReturn.add(new MazeState(maze, new Position(rowIndex + 1, columnIndex - 1), Double.POSITIVE_INFINITY, m));
        }

        return toReturn;
    }
}