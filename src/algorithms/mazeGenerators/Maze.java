package algorithms.mazeGenerators;

public class Maze {

    private int rows;
    private int columns;
    private int[][] maze;
    Position startPosition;
    Position goalPosition;

    public Maze(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        maze = new int[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int[][] getMaze() {
        return maze;
    }


    public Position getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    public Position getGoalPosition() {
        return goalPosition;
    }

    public void setGoalPosition(Position goalPosition) {
        this.goalPosition = goalPosition;
    }

    /**
     * Prints the maze
     */
    public void print() {
        for (int i = 0; i < rows; i++) {
            System.out.print("[ ");
            for (int j = 0; j < columns; j++) {
                if (i == startPosition.getRowIndex() & j == startPosition.getColumnIndex()) {
                    System.out.print("S ");
                } else if (i == goalPosition.getRowIndex() & j == goalPosition.getColumnIndex()) {
                    System.out.print("E ");
                } else {
                    System.out.print(maze[i][j] + " ");
                }
            }
            System.out.println("]");
        }
    }
}
