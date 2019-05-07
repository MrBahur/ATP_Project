package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;

import java.util.Objects;
import java.util.Properties;

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    private String tempDirectoryPath;
    private File tempDirectory;
    private File mazesDir;
    private File solutionsDir;
    private Maze mazeToSolve;
    private ObjectInputStream fromClient;
    private ObjectOutputStream toClient;
    private Solution solutionToClient;
    int numOfMazes;


    public ServerStrategySolveSearchProblem() {


        tempDirectoryPath = System.getProperty("java.io.tmpdir");
        tempDirectory = new File(tempDirectoryPath, "tempDir");
        tempDirectory.mkdir();
        mazesDir = new File(tempDirectory.getPath(), "Mazes");
        mazesDir.mkdir();
        solutionsDir = new File(tempDirectory.getPath(), "Solutions");
        solutionsDir.mkdir();
        mazeToSolve = null;
        fromClient = null;
        toClient = null;
        solutionToClient = null;
        numOfMazes = 0;
    }


    /**
     * Gets a maze from the client, solves it and sends the solution to the client.
     * All the given mazes and their solutions are kept in a temporary directory, and
     * if the given maze already exist in the directory, its solution is being pulled and sent to client.
     *
     * @param inFromClient The client's input stream
     * @param outToClient  The client's output stream
     */
    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {

        try {

            fromClient = new ObjectInputStream(inFromClient);
            toClient = new ObjectOutputStream(outToClient);
            mazeToSolve = (Maze) fromClient.readObject();

            int mazeIndex = findMaze(); //Returns -1 if maze does not exist in the mazes directory

            if (mazeIndex != -1) { //This maze was already solved and its solution is saved in the solutions directory

                loadSolution(mazeIndex);

            } else { //The maze is being solved for the first time

                solutionToClient = solveMaze();
            }

            toClient.writeObject(solutionToClient);
            //Sends the solution to the client
            toClient.flush();
            toClient.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /** Finds the solution of the maze from the solutions directory.
     *
     * @param mazeIndex The index of the maze which represents its name in the mazes directory.
     */
    private void loadSolution(int mazeIndex) {
        try {
            ObjectInputStream solutionFile =
                    new ObjectInputStream(new FileInputStream(solutionsDir.getPath() + "/" + mazeIndex));
            solutionToClient = (Solution) solutionFile.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /** Solves the new maze and adds it and its solution to the temporary directory.
     *
     * @return the solution of the maze.
     */
    private Solution solveMaze() {

        solutionToClient = null;

        try {
            OutputStream addMaze =
                    new FileOutputStream(mazesDir.getPath() + "/" + numOfMazes);
            MyCompressorOutputStream compressedMaze = new MyCompressorOutputStream(addMaze);
            compressedMaze.write(mazeToSolve.toByteArray());
            //Adds the compressed maze to the mazes directory

            addMaze.flush();
            addMaze.close();

            ObjectOutputStream addSolution =
                    new ObjectOutputStream(new FileOutputStream(solutionsDir.getPath() + "/" + numOfMazes));

            SearchableMaze searchableMaze = new SearchableMaze(mazeToSolve);

            ASearchingAlgorithm searchingAlgorithm = getSearchingAlgorithm();
            //Gets the searching algorithm from the configuration file
            solutionToClient = searchingAlgorithm.solve(searchableMaze);

            addSolution.writeObject(solutionToClient);
            //Adds a solution to the solutions directory
            addSolution.flush();
            addSolution.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return solutionToClient;
    }




    /**
     * Compares the compressed given maze to the rest of the compressed mazes in the temporary directory
     *
     * @return The index of the maze which represents the name of the maze in the temporary directory.
     * If the maze does not exist in the directory, returns -1.
     */
    private int findMaze() {

        try {
            FileOutputStream out = new FileOutputStream(tempDirectory.getPath() + "/mazeToSolve");
            MyCompressorOutputStream newMaze = new MyCompressorOutputStream(out);
            newMaze.write(mazeToSolve.toByteArray());
            //Creates a new temporary file that contains the compressed maze in order to compare it with the
            //rest of the compressed mazes in the mazes directory
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        int mazeIndex = -1;
        boolean areEqual;
        File[] mazesDirContent = mazesDir.listFiles();
        File mazeFile = null;

        numOfMazes = mazesDirContent.length;
        for (int i = 0; i < numOfMazes; i++) {

            areEqual = true;
            mazeFile = new File(mazesDir.getPath() + "/" + i);

            try {
                BufferedReader br1 = new BufferedReader(new FileReader(tempDirectory.getPath() + "/mazeToSolve"));
                BufferedReader br2 = new BufferedReader(new FileReader(mazesDir.getPath() + "/" + i));
                String line1 = null;
                String line2 = null;

                while (((line1 = br1.readLine()) != null) | ((line2 = br2.readLine()) != null)) {
                    if ((line1 == null) || (line2 == null)) {
                        areEqual = false;
                        break;
                    }
                    if (!line1.equals(line2)) {
                        areEqual = false;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Compare the files

            if (areEqual == true) {

                try {
                    mazeIndex = Integer.parseInt(mazeFile.getName());
                } catch (NumberFormatException e) {
                    mazeIndex = -1;
                }
                break;
            }
        }
        return mazeIndex;
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

