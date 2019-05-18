package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator {
    /**
     * Calculate run time of generating maze
     *
     * @param rows    the number of rows in the maze
     * @param columns the number of columns in the maze
     * @return the time it takes to generate a maze in milliseconds
     */
    @Override
    public long measureAlgorithmTimeMillis(int rows, int columns) {
        long startTime = System.currentTimeMillis();
        generate(rows, columns);
        return System.currentTimeMillis() - startTime;
    }

}
