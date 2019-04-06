package algorithms.mazeGenerators;

public class SimpleMazeGenerator extends AMazeGenerator {

    /**
     * Generates a random maze
     *
     * @param rows    the number of rows in the maze
     * @param columns the number of columns in the maze
     * @return a random maze with solution (might be more then one sol)
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
        int[][] simpleMaze = mazeResult.getMaze();
        for (int i = 0; i < rows; i++)//Creates a random maze
        {
            for (int j = 0; j < columns; j++) {
                int randomCell = (int) Math.round(Math.random()); //Random walls or cells
                simpleMaze[i][j] = randomCell;
            }
        }
        simpleMaze[0][0] = 0;
        simpleMaze[rows - 1][columns - 1] = 0;
        int k = 0, t = 0;
        boolean rowsFull = false, colsFull = false,
                downOrRight = false;// down = false, right = true
        for (int stepsCounter = 0; stepsCounter < (rows + columns - 2); stepsCounter++) //Creates a path from the start position to the end position
        {
            int randomStep = (int) (Math.random() * 2); //1 go right 0 go down
            if (randomStep == 0) {//down
                downOrRight = rowsFull;
            } else {//right
                downOrRight = !colsFull;
            }
            if (downOrRight) {
                t++;
                if (t == columns - 1) {
                    colsFull = true;
                }
            } else {
                k++;
                if (k == rows - 1) {
                    rowsFull = true;
                }
            }
            simpleMaze[k][t] = 0;


        }
        mazeResult.setStartPosition(start);
        mazeResult.setGoalPosition(goal);
        return mazeResult;
    }
}