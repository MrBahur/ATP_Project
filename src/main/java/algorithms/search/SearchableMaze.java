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
            System.out.println("Expected Maze state");
            return  null;
        }

        MazeState m = (MazeState) s;
        if (this.maze != m.getMaze()) {
            System.out.println("this is a maze state but not of this maze");
            return  null;
        }
        int rowIndex = m.getCurrentPosition().getRowIndex();
        int columnIndex = m.getCurrentPosition().getColumnIndex();
        ArrayList<AState> toReturn = new ArrayList<>();
        boolean inRangeUp = rowIndex - 1 >= 0;
        boolean inRangeDown = rowIndex + 1 < m.getMaze().getRows();
        boolean inRangeRight = columnIndex + 1 < m.getMaze().getColumns();
        boolean inRangeLeft = columnIndex - 1 >= 0;
        boolean canMoveUp = inRangeUp && m.getMaze().getMaze()[rowIndex - 1][columnIndex] == 0;
        boolean canMoveDown = inRangeDown && m.getMaze().getMaze()[rowIndex + 1][columnIndex] == 0;
        boolean canMoveRight = inRangeRight && m.getMaze().getMaze()[rowIndex][columnIndex + 1] == 0;
        boolean canMoveLeft = inRangeLeft && m.getMaze().getMaze()[rowIndex][columnIndex - 1] == 0;
        if (canMoveUp) {
            toReturn.add(new MazeState(maze, new Position(rowIndex - 1, columnIndex), 10, m));
        }
        if (canMoveDown) {
            toReturn.add(new MazeState(maze, new Position(rowIndex + 1, columnIndex), 10, m));
        }
        if (canMoveRight) {
            toReturn.add(new MazeState(maze, new Position(rowIndex, columnIndex + 1), 10, m));
        }
        if (canMoveLeft) {
            toReturn.add(new MazeState(maze, new Position(rowIndex, columnIndex - 1), 10, m));
        }
        if ((canMoveUp && inRangeRight || canMoveRight && inRangeUp) && m.getMaze().getMaze()[rowIndex - 1][columnIndex + 1] == 0) {
            toReturn.add(new MazeState(maze, new Position(rowIndex - 1, columnIndex + 1), 15, m));
        }
        if ((canMoveUp && inRangeLeft || canMoveLeft && inRangeUp) && m.getMaze().getMaze()[rowIndex - 1][columnIndex - 1] == 0) {
            toReturn.add(new MazeState(maze, new Position(rowIndex - 1, columnIndex - 1), 15, m));
        }
        if ((canMoveDown && inRangeRight || canMoveRight && inRangeDown) && m.getMaze().getMaze()[rowIndex + 1][columnIndex + 1] == 0) {
            toReturn.add(new MazeState(maze, new Position(rowIndex + 1, columnIndex + 1), 15, m));
        }
        if ((canMoveDown && inRangeLeft || canMoveLeft && inRangeDown) && m.getMaze().getMaze()[rowIndex + 1][columnIndex - 1] == 0) {
            toReturn.add(new MazeState(maze, new Position(rowIndex + 1, columnIndex - 1), 15, m));
        }


        return toReturn;
    }
}

