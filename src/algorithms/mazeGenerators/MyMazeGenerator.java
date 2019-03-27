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
        int positionsCount = 0;
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


        int edgesCount = 0;
        int randomDirection; //A number between 0-3: 0-Up, 1-Down, 2-right, 3-left
        int randomPositionIndex;

        while (mazePositions.size() > 1) {
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
                        edgesCount++;
                    }
                    case 1: //Down
                    {
                        removeSecondSidePositionFromDirections(firstOpenPositionsDirections, 0,
                                p.getRowIndex() + 2, p.getColumnIndex());
                        Position newOpenPosition = new Position(p.getRowIndex() + 1, p.getColumnIndex());
                        mergeConnectedComponents(mazePositions, p, p.getRowIndex() + 2,
                                p.getColumnIndex(), newOpenPosition);
                        edgesCount++;
                    }
                    case 2: //Right
                    {
                        removeSecondSidePositionFromDirections(firstOpenPositionsDirections, 3,
                                p.getRowIndex(), p.getColumnIndex() + 2);
                        Position newOpenPosition = new Position(p.getRowIndex(), p.getColumnIndex() + 1);
                        mergeConnectedComponents(mazePositions, p, p.getRowIndex(),
                                p.getColumnIndex() + 2, newOpenPosition);
                        edgesCount++;
                    }
                    case 3: //Left
                    {
                        removeSecondSidePositionFromDirections(firstOpenPositionsDirections, 2,
                                p.getRowIndex(), p.getColumnIndex() - 2);
                        Position newOpenPosition = new Position(p.getRowIndex(), p.getColumnIndex() - 1);
                        mergeConnectedComponents(mazePositions, p, p.getRowIndex(),
                                p.getColumnIndex() - 2, newOpenPosition);
                        edgesCount++;
                    }
                }

            }

        }
        for (int i = 0; i < maze.getRows() ; i++) {
            for (int j = 0; j < maze.getColumns(); j++) {
                maze.getMaze()[i][j] =1;
            }
        }
        for (int i = 0; i < mazePositions.size() ; i++) {
            for (int j = 0; j < mazePositions.get(i).size(); j++) {
                Position p = mazePositions.get(i).get(j);
                int row = p.getRowIndex();
                int col = p.getColumnIndex();
                maze.getMaze()[row][col] = 0;
            }
        }
        maze.setStartPosition(new Position(1,1));
        maze.setGoalPosition(new Position(maze.getRows()-1, maze.getColumns()-1));
        return maze;
    }


    private void removeSecondSidePositionFromDirections(ArrayList<ArrayList<Position>> firstOpenPositionsDirections,
                                                        int oppositeDirection, int secondSidePositionRow,
                                                        int secondSidePositionColumn) {
        for (int i = 0; i < firstOpenPositionsDirections.get(oppositeDirection).size(); i++) {
            Position secondSideOfTheEdge = firstOpenPositionsDirections.get(oppositeDirection).get(i);
            if (secondSideOfTheEdge.getRowIndex() == secondSidePositionRow &
                    secondSideOfTheEdge.getColumnIndex() == secondSidePositionColumn) {
                firstOpenPositionsDirections.get(oppositeDirection).remove(i);
            break;
            }
            }
    }

    private void mergeConnectedComponents(ArrayList<ArrayList<Position>> mazePositions,
                                          Position p,
                                          int secondSidePositionRow,
                                          int secondSidePositionColumn,
                                          Position newOpenPosition) {
        int pConnectedComponentIndex = -1;
        for (int i = 0; i < mazePositions.size(); i++) {
            for (int j = 0; j < mazePositions.get(i).size(); j++) {
                Position pSearch = mazePositions.get(i).get(j);
                if (pSearch.getRowIndex() == p.getRowIndex() &
                        pSearch.getColumnIndex() == p.getColumnIndex())
                    pConnectedComponentIndex = i;
            }
        }
        for (int i = 0; i < mazePositions.size(); i++) {
            for (int j = 0; j < mazePositions.get(i).size(); j++) {
                Position secondSidePosition = mazePositions.get(i).get(j);
                if (secondSidePosition.getRowIndex() == secondSidePositionRow &
                        secondSidePosition.getColumnIndex() == secondSidePositionColumn) {
                    if (i != pConnectedComponentIndex & pConnectedComponentIndex != -1)
                    //Make sure that both of the positions don't already have the same connected component in order
                    //to prevent closing a circle
                    {
                        mazePositions.get(pConnectedComponentIndex).add(newOpenPosition);
                        while (!mazePositions.get(i).isEmpty())//Merge the connected components of both positions of the edge
                        {
                            mazePositions.get(pConnectedComponentIndex).add(mazePositions.get(i).get(0));
                            mazePositions.get(i).remove(0);
                        }
                        if(mazePositions.get(i).isEmpty()) mazePositions.remove(i);
                        return;
                    }
                }
            }
        }
    }
}

//        ArrayList<Position> vertices = new ArrayList<>();
//        ArrayList<Pair<Position, Position>> edges = new ArrayList<>();
//        Maze maze = new Maze(rows, cols);
//
//        for (int i = 1; i < rows; i += 2) {
//            for (int j = 1; j < cols; j += 2) {
//                Position p1 = new Position(i, j);
//                vertices.add(p1);
//            }
//        }//Adds each position to vertices
//        int edgeIndex = 0;
//        for (int i = 0; i < vertices.size(); i++) {
//            Position p1 = vertices.get(i);
//            if (p1.getRowIndex() + 2 < rows)//Adds an edge between p1 vertex and the vertex beneath it
//            {
//                Position p2 = vertices.get(i + cols / 2);
//                Pair<Position, Position> newPair = new Pair<Position, Position>(p1, p2);
//                edges.add(edgeIndex, newPair);
//                edgeIndex++;
//            }
//            if (p1.getColumnIndex() + 2 < cols) //Adds an edge between p1 vertex and the vertex on his right
//            {
//                Position p2 = vertices.get(i + 1);
//                Pair<Position, Position> newPair = new Pair<Position, Position>(p1, p2);
//                edges.add(edgeIndex, newPair);
//                edgeIndex++;
//            }
//        }
//
//        ArrayList<ArrayList<Position>> mazePositions = new ArrayList<ArrayList<Position>>();
//        int edgesCount = 0;
//        while (edgesCount < vertices.size() - 1) {
//
//        }

//  ArrayList<Pair<Position, Integer>> mazePositions = new ArrayList<>();//Pairs of positions and their connecting component's index

//        while (mazePositions.size() < vertices.size() - 1) {
//            int randomEdgeIndex = (int) (Math.random() * edges.size());
//            Pair<Position, Position> newEdge = edges.get(randomEdgeIndex);
//            Position p1 = newEdge.getKey();
//            Position p2 = newEdge.getValue();
//            for (int i = 0; i < mazePositions.size(); i++) {
//                if (p1.getRowIndex() == mazePositions.get(i).getKey().getRowIndex() &
//                p1.getColumnIndex() == mazePositions.get(i).getKey().getColumnIndex()) {
//                    Pair<Position,Integer> e1 = mazePositions.get(i);
//                    for (int j = 0; j < mazePositions.size(); j++) {
//                        if (p2.getRowIndex() == mazePositions.get(j).getKey().getRowIndex() &
//                        p2.getColumnIndex() == mazePositions.get(j).getKey().getColumnIndex()){
//                            Pair<Position,Integer> e2 = mazePositions.get(j);
//                            if(e1.getValue().equals(e2.getValue()) == false)
//                            {
//                                Integer e2ConnectingComponentIndex = e2.getValue();
//                                for (int k = 0; k < mazePositions.size(); k++) {
//                                    if(mazePositions.get(k).getValue().equals(e2ConnectingComponentIndex) == true){
//                                        mazePositions.get(k).getValue() = e1.getValue();
//                                    }
//                                }
//                            }
//
//
//                        }
//                    }
//                }
//            }
//        }
//        return maze;
//}
//
//
//}
