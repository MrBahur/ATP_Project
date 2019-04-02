package algorithms.mazeGenerators;

public class SimpleMazeGenerator extends AMazeGenerator {

    @Override
    public Maze generate(int rows, int columns) {
        if (rows <= 0 || columns <= 0)
            return null;
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