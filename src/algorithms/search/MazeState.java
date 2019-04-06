package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

public class MazeState extends AState {

    private Maze maze;
    private Position currentPosition;

    //region Constructors
    public MazeState(Maze maze, Position currentPosition, double cost, MazeState previousState) {
        super(currentPosition.toString(), cost, previousState);
        this.maze = maze;
        this.currentPosition = currentPosition;
    }
    //endregion

    //region Getters
    public Position getCurrentPosition() {
        return currentPosition;
    }

    public Maze getMaze() {
        return maze;
    }
    //endregion

    /**
     * Overriding equals for MazeState
     *
     * @param o another State of the same maze
     * @return true if the state represent the same state in the maze
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MazeState))
            return false;
        MazeState other = (MazeState) o;
        return other.currentPosition.equals(currentPosition);
    }
}
