package algorithms.mazeGenerators;

public class Maze{

    private int rows;
    private int columns;
    private int[][] maze;
    Position startPosition;
    Position goalPosition;

    public Maze(int rows, int columns, int[][] maze, Position start, Position end) {
        this.rows = rows;
        this.columns = columns;
        this.maze = maze;
        this.startPosition = start;
        this.goalPosition = end;
    }

    public void setMaze(int[][] maze) {
        this.maze = maze;
    }

    public int[][] getMaze() {
        return maze;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public Position getGoalPosition() {
        return goalPosition;
    }
}
