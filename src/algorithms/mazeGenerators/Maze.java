package algorithms.mazeGenerators;

public class Maze {

    private int[][] maze;
    private Position startPosition;
    private Position endPosition;

    public Maze(int rows, int cols) {
        maze = new int[rows][cols];
    }

    //TODO implement this method, S for start, E for exit
    public void print() {

    }


    //<editor-fold desc="Getters">
    //TODO implement
    public Position getStartPosition() {
        return null;
    }

    //TODO implement

    public Position getGoalPosition() {
        return null;
    }
    //</editor-fold>


    //<editor-fold desc="Setters">
    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    public void setEndPosition(Position endPosition) {
        this.endPosition = endPosition;
    }
    //</editor-fold>
}
