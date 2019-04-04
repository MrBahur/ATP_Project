package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator {
    /**
     *Returns the time it takes to generate a maze in milliseconds
     * @param rows - the number of rows in the maze
     * @param columns - the number of columns in the maze
     * @return
     */
    @Override
    public long measureAlgorithmTimeMillis(int rows, int columns) {
        long startTime = System.currentTimeMillis();
        generate(rows, columns);
        return System.currentTimeMillis() - startTime;
    }

}
