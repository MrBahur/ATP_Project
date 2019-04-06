package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator {
    /**
     * Generates an all zero's maze
     *
     * @param rows    - the number of rows in the maze
     * @param columns - - the number of columns in the maze
     * @return a Maze without walls
     */
    @Override
    public Maze generate(int rows, int columns) {
        if (rows <= 0 || columns <= 0) {
            rows = 10;
            columns = 10;
            System.out.println("wrong parameters sent to generate so a maze in size 10X10 created");
        }
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
