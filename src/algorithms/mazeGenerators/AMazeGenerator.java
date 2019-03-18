package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator{

    abstract public Maze generate(int row, int cols);

    //TODO implement this method:
    @Override
    public long measureAlgorithmTimeMillis(int row, int col) {
        return 0;
    }
}
