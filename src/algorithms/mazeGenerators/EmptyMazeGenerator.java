package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator {

    @Override
    public Maze generate(int rows, int columns) {
        if (rows <= 0 || columns <= 0)
            return null;
        Maze mazeResult = new Maze(rows, columns);
        Position start = new Position(0, 0);
        Position goal = new Position(rows - 1, columns - 1);
        int[][] emptyMaze = mazeResult.getMaze();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                emptyMaze[i][j] = 0;
            }
        }
        mazeResult.setStartPosition(start);
        mazeResult.setGoalPosition(goal);

        return mazeResult;
    }
}
