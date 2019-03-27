package algorithms.mazeGenerators;

import javafx.geometry.Pos;
import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MyMazeGenerator extends AMazeGenerator {

    @Override
    public Maze generate(int rows, int cols) {

        Maze maze = new Maze(rows, cols);
        ArrayList<ArrayList<Position>> firstOpenPositionsDirections = new ArrayList<ArrayList<Position>>();
        ArrayList<ArrayList<Position>> mazePositions = new ArrayList<ArrayList<Position>>();
        initializeArraysAndPositions(firstOpenPositionsDirections,mazePositions,rows,cols);


        while (mazePositions.size() > 1) {
            createAnEdgeAndAddItToTheMaze(firstOpenPositionsDirections,mazePositions);
        }
        return createFinaleMaze(maze,mazePositions);
    }

    private void initializeArraysAndPositions(ArrayList<ArrayList<Position>> firstOpenPositionsDirections,
                                             ArrayList<ArrayList<Position>> mazePositions, int rows,int cols){
        int positionsCount=0;
        for (int i = 0; i < 4; i++) {
            firstOpenPositionsDirections.add(new ArrayList<Position>());
        }
        for (int i = 1; i < rows; i += 2) {
            //Creates an ArrayList of directions - in each index there is an ArrayList of all the positions that has an
            //open position in the specific direction that the index represents
            //0-up, 1-down, 2-right, 3-left
            for (int j = 1; j < cols; j += 2) {
                Position p = new Position(i, j);
                mazePositions.add(new ArrayList<Position>());
                mazePositions.get(positionsCount).add(p);//At the beginning every list on the mazePositions has a single node
                positionsCount++;
                if (i - 2 >= 0) firstOpenPositionsDirections.get(0).add(p); //p has an open position above it
                if (i + 2 < rows) firstOpenPositionsDirections.get(1).add(p); //p has an open position beneath it
                if (j + 2 < cols) firstOpenPositionsDirections.get(2).add(p);//p has an open position on it's right
                if (j - 2 >= 0) firstOpenPositionsDirections.get(3).add(p);//p has an open position on it's left
            }
        }
    }
    private void createAnEdgeAndAddItToTheMaze(ArrayList < ArrayList < Position >> firstOpenPositionsDirections,
             ArrayList < ArrayList < Position >> mazePositions)
    {
        int randomDirection; //A number between 0-3: 0-Up, 1-Down, 2-right, 3-left
        int randomPositionIndex;
        randomDirection = (int) (Math.random() * 4);
        if (firstOpenPositionsDirections.get(randomDirection).size() != 0) {
            randomPositionIndex = (int) (Math.random() * firstOpenPositionsDirections.get(randomDirection).size());
            Position p = firstOpenPositionsDirections.get(randomDirection).get(randomPositionIndex);

            firstOpenPositionsDirections.get(randomDirection).remove(randomPositionIndex);
            //Remove the selected Position from the selected direction

            switch (randomDirection) {
                case 0: //Up
                {
                    removeSecondSidePositionFromDirections(firstOpenPositionsDirections, 1,
                            p.getRowIndex() - 2, p.getColumnIndex());
                    Position newOpenPosition = new Position(p.getRowIndex() - 1, p.getColumnIndex());
                    mergeConnectedComponents(mazePositions, p, p.getRowIndex() - 2,
                            p.getColumnIndex(), newOpenPosition);
                    break;
                }
                case 1: //Down
                {
                    removeSecondSidePositionFromDirections(firstOpenPositionsDirections, 0,
                            p.getRowIndex() + 2, p.getColumnIndex());
                    Position newOpenPosition = new Position(p.getRowIndex() + 1, p.getColumnIndex());
                    mergeConnectedComponents(mazePositions, p, p.getRowIndex() + 2,
                            p.getColumnIndex(), newOpenPosition);
                    break;
                }
                case 2: //Right
                {
                    removeSecondSidePositionFromDirections(firstOpenPositionsDirections, 3,
                            p.getRowIndex(), p.getColumnIndex() + 2);
                    Position newOpenPosition = new Position(p.getRowIndex(), p.getColumnIndex() + 1);
                    mergeConnectedComponents(mazePositions, p, p.getRowIndex(),
                            p.getColumnIndex() + 2, newOpenPosition);
                    break;
                }
                case 3: //Left
                {
                    removeSecondSidePositionFromDirections(firstOpenPositionsDirections, 2,
                            p.getRowIndex(), p.getColumnIndex() - 2);
                    Position newOpenPosition = new Position(p.getRowIndex(), p.getColumnIndex() - 1);
                    mergeConnectedComponents(mazePositions, p, p.getRowIndex(),
                            p.getColumnIndex() - 2, newOpenPosition);
                    break;
                }
            }
        }
    }
    private void removeSecondSidePositionFromDirections(ArrayList<ArrayList<Position>> firstOpenPositionsDirections,
                                                        int oppositeDirection, int secondSidePositionRow,
                                                        int secondSidePositionCol) {
        Position secondSidePosition = new Position(secondSidePositionRow,secondSidePositionCol);
        firstOpenPositionsDirections.get(oppositeDirection).remove(secondSidePosition);
    }

    private void mergeConnectedComponents
            (ArrayList<ArrayList<Position>> mazePositions, Position p,
             int secondSidePositionRow, int secondSidePositionColumn, Position newOpenPosition) {

        int pConnectedComponentIndex = getComponentIndex(mazePositions, p);
        int secondSideConnectedComponent =
                getComponentIndex(mazePositions, new Position(secondSidePositionRow, secondSidePositionColumn));

        if (pConnectedComponentIndex != secondSideConnectedComponent)
        //Make sure that both of the positions don't already have the same connected component in order
        //to prevent closing a circle
        {
            mazePositions.get(pConnectedComponentIndex).add(newOpenPosition);
            while (!(mazePositions.get(secondSideConnectedComponent).isEmpty()))
                //Merge the connected components of both positions of the edge
            {
                mazePositions.get(pConnectedComponentIndex).add(mazePositions.get(secondSideConnectedComponent).get(0));
                mazePositions.get(secondSideConnectedComponent).remove(0);
            }
            if (mazePositions.get(secondSideConnectedComponent).isEmpty())
                mazePositions.remove(secondSideConnectedComponent);
        }
        return;
    }

    private int getComponentIndex(ArrayList<ArrayList<Position>> mazePositions, Position p){
        int pConnectedComponentIndex = -1;
        for (int i = 0; i < mazePositions.size(); i++) {
            for (int j = 0; j < mazePositions.get(i).size(); j++) {
                Position pSearch = mazePositions.get(i).get(j);
                if (p.equals(pSearch) == true) {
                    pConnectedComponentIndex = i;
                    return pConnectedComponentIndex;
                }
            }
        }
        return -1;
    }

    private Maze createFinaleMaze(Maze maze, ArrayList<ArrayList<Position>> mazePositions){
        for (int i = 0; i < maze.getRows(); i++) {
            for (int j = 0; j < maze.getColumns(); j++) {
                maze.getMaze()[i][j] = 1;
            }
        }
        for (int i = 0; i < mazePositions.size(); i++) {
            for (int j = 0; j < mazePositions.get(i).size(); j++) {
                Position p = mazePositions.get(i).get(j);
                int row = p.getRowIndex();
                int col = p.getColumnIndex();
                maze.getMaze()[row][col] = 0;
            }
        }
        maze.setStartPosition(new Position(1, 1));
        maze.setGoalPosition(new Position(maze.getRows() - 1, maze.getColumns() - 1));
        return maze;
    }
}

