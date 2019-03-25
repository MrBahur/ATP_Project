package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator {


    @Override
    public long measureAlgorithmTimeMillis(int rows, int columns) {
        long timeResult;
        long startTime = System.currentTimeMillis();
        generate(rows, columns);
        long endTime = System.currentTimeMillis();
        timeResult = endTime - startTime;
        return timeResult;
    }

}
