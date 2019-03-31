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

        int xPosition = m.getCurrentPosition().getRowIndex();
        int yPosition = m.getCurrentPosition().getColumnIndex();
        ArrayList<AState> toReturn = new ArrayList<>();
        boolean canMoveUp = yPosition + 1 < maze.getRows() && maze.getMaze()[xPosition][yPosition + 1] == 0;
        boolean canMoveDown = yPosition - 1 >= 0 && maze.getMaze()[xPosition][yPosition - 1] == 0;
        boolean canMoveRight = xPosition + 1 < maze.getColumns() && maze.getMaze()[xPosition + 1][yPosition] == 0;
        boolean canMoveLeft = xPosition - 1 > 0 && maze.getMaze()[xPosition - 1][yPosition] == 0;
        if (canMoveUp) {
            toReturn.add(new MazeState(maze, new Position(xPosition, yPosition + 1), Double.POSITIVE_INFINITY, m));
        }
        if (canMoveDown) {
            toReturn.add(new MazeState(maze, new Position(xPosition, yPosition - 1), Double.POSITIVE_INFINITY, m));
        }
        if (canMoveRight) {
            toReturn.add(new MazeState(maze, new Position(xPosition + 1, yPosition), Double.POSITIVE_INFINITY, m));
        }
        if (canMoveLeft) {
            toReturn.add(new MazeState(maze, new Position(xPosition - 1, yPosition), Double.POSITIVE_INFINITY, m));
        }
        if (canMoveUp & canMoveRight) {
            toReturn.add(new MazeState(maze, new Position(xPosition + 1, yPosition + 1), Double.POSITIVE_INFINITY, m));
        }
        if (canMoveUp & canMoveLeft) {
            toReturn.add(new MazeState(maze, new Position(xPosition - 1, yPosition + 1), Double.POSITIVE_INFINITY, m));
        }
        if (canMoveDown & canMoveRight) {
            toReturn.add(new MazeState(maze, new Position(xPosition + 1, yPosition - 1), Double.POSITIVE_INFINITY, m));
        }
        if (canMoveDown & canMoveLeft) {
            toReturn.add(new MazeState(maze, new Position(xPosition - 1, yPosition - 1), Double.POSITIVE_INFINITY, m));
        }

        return toReturn;
    }
}