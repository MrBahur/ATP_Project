package algorithms.mazeGenerators;

public class SimpleMazeGenerator extends AMazeGenerator {

    public SimpleMazeGenerator(Maze mazeToGenerate) {
        super(mazeToGenerate);
    }

    @Override
    public Maze generate(int rows, int columns)
    {
        if (rows <= 0 || columns <= 0)
            return null;
        Position start = new Position(0, 0);
        Position end = new Position(rows - 1, columns - 1);
        int[][] simpleMaze = new int[rows][columns];
        for(int i=0; i<rows; i++)//Creates a random maze
        {
            for(int j=0; j<columns; j++)
            {
                int randomCell = (int) Math.round(Math.random()); //Random walls or cells
                simpleMaze[i][j] = randomCell;
            }
        }
        for(int k=0,t=0;k<rows||t<columns;) //Creates a path from the start position to the end position
        {
            int randomStep = (int)Math.round(Math.random());
            if(randomStep==1)//Step down
            {
                if(k+1<rows)
                    simpleMaze[k+1][t] = 0;
                k++;
            }
            else if(t+1<columns)
                simpleMaze[k][t+1] = 0;
            t++;
        }
//
        Maze mazeResult = new Maze(rows, columns, simpleMaze, start, end);
        this.mazeToGenerate = mazeResult;
        return this.mazeToGenerate;
    }
}