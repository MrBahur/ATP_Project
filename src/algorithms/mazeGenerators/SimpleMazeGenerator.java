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
        int k=0, t=0;
        boolean rowsFull = false, colsFull = false;
        for (int stepsCounter=0; stepsCounter < rows+columns; stepsCounter++) //Creates a path from the start position to the end position
        {
            int randomStep = (int) Math.round(Math.random());
            if (randomStep == 1)//Step down
            {
                if (rowsFull==false){
                    simpleMaze[k][t] = 0;
                    k++;
                    if(k==rows){
                        k--;
                        rowsFull=true;
                    }
                }
            } else if (colsFull == false)//Go right
            {
                simpleMaze[k][t] = 0;
                t++;
                if (t==columns) {
                    t--;
                    colsFull = true;
                }
            }
        }
        mazeResult.setStartPosition(start);
        mazeResult.setGoalPosition(goal);
        return mazeResult;
    }
}