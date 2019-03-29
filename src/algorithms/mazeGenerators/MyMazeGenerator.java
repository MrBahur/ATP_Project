package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Stack;

public class MyMazeGenerator extends AMazeGenerator {
    @Override
    public Maze generate(int rows, int cols) {

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
}