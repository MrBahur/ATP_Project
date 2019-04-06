package algorithms.mazeGenerators;

public interface IMazeGenerator {

    /**
     * generate a maze
     *
     * @param rows    the number of rows in the maze
     * @param columns the number of columns in the maze
     * @return a maze represented with 1's and 0's
     */
    Maze generate(int rows, int columns);

    /**
     * Calculate run time of generating maze
     *
     * @param rows    the number of rows in the maze
     * @param columns the number of columns in the maze
     * @return the time it takes to generate a maze in milliseconds
     */
    long measureAlgorithmTimeMillis(int rows, int columns);
}
