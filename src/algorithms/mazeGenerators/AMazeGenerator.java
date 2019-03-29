package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator {


    @Override
    public long measureAlgorithmTimeMillis(int rows, int columns) {
        long startTime = System.currentTimeMillis();
        generate(rows, columns);
        return System.currentTimeMillis() - startTime;
    }

}
