package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Stack;

public class MyMazeGenerator extends AMazeGenerator {

    /**
     * Generates a maze
     * @param rows - number of rows in the maze
     * @param cols - number of columns in the maze
     * @return a generated maze
     */
    @Override
    public Maze generate(int rows, int cols) {

        if (rows < 0 || cols < 0) {
            return null;
        }
        int chooseAlgorithm = (int) (Math.random() * 2);
        if (chooseAlgorithm == 0) {
            return dfsAlgorithm(rows, cols); // Generates a maze with DFS algorithm
        } else {
            return primsAlgorithm(rows, cols); // Generates a maze with Prim's algorithm
        }

    }

    private Position nextCell(Position currentCell, Position nextCell, int[][] mazeMatrix, boolean[][] visited) {
        int wallRowIndex = (currentCell.getRowIndex() + nextCell.getRowIndex()) / 2;
        int wallCallIndex = (currentCell.getColumnIndex() + nextCell.getColumnIndex()) / 2;
        visited[nextCell.getRowIndex()][nextCell.getColumnIndex()] = true;//mark new cell as visited
        mazeMatrix[wallRowIndex][wallCallIndex] = 0;//remove wall
        mazeMatrix[nextCell.getRowIndex()][nextCell.getColumnIndex()] = 0;
        return nextCell;
    }

    private ArrayList<Position> getNotVisitedNeighbours(boolean[][] visited, Position cell) {
        ArrayList<Position> p = new ArrayList<>();
        int rowIndex = cell.getRowIndex();
        int colIndex = cell.getColumnIndex();
        int rows = visited.length;
        int cols = visited[0].length;
        if (rowIndex + 2 < rows && !visited[rowIndex + 2][colIndex]) {//UP
            p.add(new Position(rowIndex + 2, colIndex));
        }
        if (colIndex + 2 < cols && !visited[rowIndex][colIndex + 2]) {//RIGHT
            p.add(new Position(rowIndex, colIndex + 2));
        }
        if (rowIndex - 2 >= 0 && !visited[rowIndex - 2][colIndex]) {//DOWN
            p.add(new Position(rowIndex - 2, colIndex));
        }
        if (colIndex - 2 >= 0 && !visited[rowIndex][colIndex - 2]) {//LEFT
            p.add(new Position(rowIndex, colIndex - 2));
        }

        if (p.size() == 0) {
            return null;
        } else {
            return p;
        }
    }

    /**
     * Generates a maze with DFS algorithm
     */
    private Maze dfsAlgorithm(int rows, int cols) {
        Maze maze = new Maze(rows, cols);
        boolean visited[][] = new boolean[rows][cols];//0 - for white, 1 for grey, 2 for black
        int[][] mazeMatrix = maze.getMaze();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                visited[i][j] = false;
                mazeMatrix[i][j] = 1;
            }
        }
        Position currentCell = new Position((int) (Math.random() * (rows)), (int) (Math.random() * (cols)));
        maze.setStartPosition(currentCell);
        mazeMatrix[currentCell.getRowIndex()][currentCell.getColumnIndex()] = 0;
        visited[currentCell.getRowIndex()][currentCell.getColumnIndex()] = true;
        Stack<Position> stack = new Stack<>();
        Position lastPosition = currentCell;
        int longestRoad = 0;
        do {
            ArrayList<Position> notVisitedNeighbours = null;
            if ((notVisitedNeighbours = getNotVisitedNeighbours(visited, currentCell)) != null) {
                int numOfNeighbours = notVisitedNeighbours.size();
                int chosen = (int) (Math.random() * numOfNeighbours);
                stack.push(currentCell);
                currentCell = nextCell(currentCell, notVisitedNeighbours.get(chosen), mazeMatrix, visited);
                if (stack.size() > longestRoad) {
                    longestRoad = stack.size();
                    lastPosition = currentCell;
                }
            } else {
                currentCell = stack.pop();
            }
        } while (!stack.empty());
        maze.setGoalPosition(lastPosition);
        return maze;
    }

/**
 * Generates a maze with Prim's algorithm
 */
    private Maze primsAlgorithm(int rows, int cols) {

        Maze maze = new Maze(rows, cols);
        ArrayList<Position> neighbors = new ArrayList<>();
        initializeMazeArray(maze);
        setMaze(maze, neighbors);
        setStartAndGoalPositions(maze);
        return maze;

    }

    /**
     * Creates an all walls maze
     */
    private void initializeMazeArray(Maze maze) {

        for (int i = 0; i < maze.getMaze().length; i++) {
            for (int j = 0; j < maze.getMaze()[i].length; j++) {
                maze.getMaze()[i][j] = 1;
            }
        }
    }

    private void addACellToMaze(Maze maze, Position p, ArrayList<Position> neighbors, boolean neighborConnected) {

        int pRow = p.getRowIndex();
        int pCol = p.getColumnIndex();
        maze.getMaze()[pRow][pCol] = 0;

        int upPosition = -2;
        int downPosition = -2;
        int leftPosition = -2;
        int rightPosition = -2;
        if (pRow - 2 >= 0)
            upPosition = maze.getMaze()[pRow - 2][pCol];
        if (pRow + 2 < maze.getMaze().length)
            downPosition = maze.getMaze()[pRow + 2][pCol];
        if (pCol - 2 >= 0)
            leftPosition = maze.getMaze()[pRow][pCol - 2];
        if (pCol + 2 < maze.getMaze()[0].length)
            rightPosition = maze.getMaze()[pRow][pCol + 2];

        // 0-openCell, -1-possible neighbors , -2-already opened cell
        if (upPosition != 0 & upPosition != -1 & upPosition != -2) {
            neighbors.add(new Position(pRow - 2, pCol));
            maze.getMaze()[pRow - 2][pCol] = -1;
        }
        if (downPosition != 0 & downPosition != -1 & downPosition != -2) {
            neighbors.add(new Position(pRow + 2, pCol));
            maze.getMaze()[pRow + 2][pCol] = -1;
        }
        if (leftPosition != 0 & leftPosition != -1 & leftPosition != -2) {
            neighbors.add(new Position(pRow, pCol - 2));
            maze.getMaze()[pRow][pCol - 2] = -1;
        }
        if (rightPosition != 0 & rightPosition != -1 & rightPosition != -2) {
            neighbors.add(new Position(pRow, pCol + 2));
            maze.getMaze()[pRow][pCol + 2] = -1;
        }

        while (neighborConnected == false) {//Opens one of the positions that is a neighbor of an already opened position
            int randNeighbor = (int) (Math.random() * 4);
            if (randNeighbor == 0) {
                if (upPosition == 0) {
                    maze.getMaze()[pRow - 1][pCol] = 0;
                    neighborConnected = true;
                }
            } else if (randNeighbor == 1) {
                if (downPosition == 0) {
                    maze.getMaze()[pRow + 1][pCol] = 0;
                    neighborConnected = true;
                }
            } else if (randNeighbor == 2) {
                if (leftPosition == 0) {
                    maze.getMaze()[pRow][pCol - 1] = 0;
                    neighborConnected = true;
                }
            } else if (randNeighbor == 3) {
                if (rightPosition == 0) {
                    maze.getMaze()[pRow][pCol + 1] = 0;
                    neighborConnected = true;
                }
            }
        }
    }

    /**
     * Generates the matrix of the maze
     */
    private void setMaze(Maze maze, ArrayList<Position> neighbors) {
        int randomPositionRow = (int) (Math.random() * (maze.getMaze().length - 1));
        int randomPositionColumn = (int) (Math.random() * (maze.getMaze()[0].length - 1));
        Position firstCell = new Position(randomPositionRow, randomPositionColumn);
        addACellToMaze(maze, firstCell, neighbors, true);
        while (neighbors.size() > 0) {
            int randomNeighbor = (int) (Math.random() * neighbors.size());
            if (randomNeighbor < neighbors.size()) {
                Position p = neighbors.remove(randomNeighbor);
                addACellToMaze(maze, p, neighbors, false);
            }
        }
    }

    /**
     * After the maze is generated sets the start and end positions
     */
    private void setStartAndGoalPositions(Maze maze) {
        int maxRow = 0;
        int maxCol = 0;
        int minRow = maze.getRows() - 1;
        int minCol = maze.getColumns() - 1;

        for (int i = 0; i < maze.getMaze().length; i++) {
            for (int j = 0; j < maze.getMaze()[i].length; j++) {
                if (maze.getMaze()[i][j] == 0) {
                    if (i > maxRow)
                        maxRow = i;
                    if (j > maxCol)
                        maxCol = j;
                    if (i < minRow)
                        minRow = i;
                    if (j < minCol)
                        minCol = j;
                }
            }
        }
        maze.setStartPosition(new Position(minRow, minCol));
        maze.setGoalPosition(new Position(maxRow, maxCol));
    }
}