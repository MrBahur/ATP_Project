package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator{

    abstract public Maze generate(int row, int cols);

    //TODO test this method:
    @Override
    public long measureAlgorithmTimeMillis(int row, int col) {
        long time = System.currentTimeMillis();
        generate(row,col);
        return System.currentTimeMillis()-time;
    }
}
