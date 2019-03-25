package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator {


    public EmptyMazeGenerator(Maze mazeToGenerate) {
        super(mazeToGenerate);
    }

    @Override
    public Maze generate(int rows, int columns) {
        if(rows<=0 || columns<=0)
            return null;
        Position start = new Position(0,0);
        Position end = new Position(rows-1,columns-1);
        int[][] emptyMaze = new int[rows][columns];
        for(int i=0; i<rows; i++)
        {
            for(int j=0; j<columns; j++)
            {
                emptyMaze[i][j]=0;
            }
        }
        Maze mazeResult = new Maze(rows, columns, emptyMaze, start, end);
        this.mazeToGenerate = mazeResult;
        return this.mazeToGenerate;
    }
}
