package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

public class MazeState extends AState {

    Maze maze;
    Position currentPosition;

    public MazeState(Maze maze, Position currentPosition) {
        super(currentPosition.toString());
        this.maze = maze;
        this.currentPosition = currentPosition;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public Maze getMaze() {
        return maze;
    }


    @Override
    public boolean equals(AState aState) {
        if (!(aState instanceof MazeState))
            return false;
        MazeState other = (MazeState) aState;
        return other.currentPosition.equals(currentPosition);
    }
}
