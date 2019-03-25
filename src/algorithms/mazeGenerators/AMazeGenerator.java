package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator {

    protected Maze mazeToGenerate;

    public AMazeGenerator(Maze mazeToGenerate) {
        this.mazeToGenerate = mazeToGenerate;
    }

    public void setMazeToGenerate(Maze mazeToGenerate) {
        this.mazeToGenerate = mazeToGenerate;
    }

    public Maze getMazeToGenerate() {
        return mazeToGenerate;
    }

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
