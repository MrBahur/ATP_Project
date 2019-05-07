package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;
import java.util.Objects;

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
                ASearchingAlgorithm searcher = getSearchingAlgorithm();
                Solution s = searcher.solve(new SearchableMaze(m));
                toClient.writeObject(s);
                //write the solution
            }


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private boolean isSolutionExist(Maze m) {
        return false;
    }

    private ASearchingAlgorithm getSearchingAlgorithm() {
        if (Objects.equals(Configurations.getSearchingAlgorithm(), "BestFirstSearch")) {
            return new BestFirstSearch();
        } else if (Objects.equals(Configurations.getSearchingAlgorithm(), "DepthFirstSearch")) {
            return new DepthFirstSearch();
        } else if (Objects.equals(Configurations.getSearchingAlgorithm(), "BreadthFirstSearch")) {
            return new BreadthFirstSearch();
        } else return new BestFirstSearch();//default case
    }
}