package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.ASearchingAlgorithm;
import algorithms.search.BestFirstSearch;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;

import java.io.*;

public class ServerStrategySolveSearchProblem implements IServerStrategy {
    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);

            Maze m = (Maze) fromClient.readObject();
            //TODO fill this case
            if (isSolutionExist(m)) {

            } else {
                ASearchingAlgorithm searcher = new BestFirstSearch();
                Solution s = searcher.solve(new SearchableMaze(m));
                toClient.writeObject(s);
            }


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private boolean isSolutionExist(Maze m) {
        return false;
    }
}