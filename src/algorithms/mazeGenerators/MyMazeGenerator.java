package algorithms.mazeGenerators;

import javafx.geometry.Pos;
import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MyMazeGenerator extends AMazeGenerator{


    @Override
    public Maze generate(int row, int cols) {
        ArrayList<Position> vertices = new ArrayList<>();
        ArrayList<Pair<Position,Position>> edges = new ArrayList<>();
        Maze maze = new Maze(row,cols);

        for (int i = 1; i < row ; i+=2) {
            for (int j = 1; j < cols ; j+=2) {
                Position p1 = new Position(i,j);
                vertices.add(p1);
            }
        }
        for (int i = 0; i <vertices.size() ; i++) {
            Position p = vertices.get(i);
            createEdgesForVertex(p,edges);
        }


        return maze;
    }

    private void createEdgesForVertex(Position p, ArrayList<Pair<Position, Position>> edges) {

    }
}
