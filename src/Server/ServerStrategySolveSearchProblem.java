package Server;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.search.DepthFirstSearch;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;

import java.io.*;

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    private String tempDirectoryPath;
    private File tempDirectory;
    private File mazesDir;
    private File solutionsDir;
    private Maze mazeToSolve;
    private ObjectInputStream fromClient;
    private ObjectOutputStream toClient;
    private Solution solutionToClient;


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

    }

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {

        System.out.println(mazesDir.getPath());
        try {
            fromClient = new ObjectInputStream(inFromClient);
            toClient = new ObjectOutputStream(outToClient);

            mazeToSolve = (Maze) fromClient.readObject();
            solutionToClient = null;
            boolean areEqual = false;

            FileOutputStream out = new FileOutputStream(tempDirectory.getPath() + "/mazeToSolve");
            MyCompressorOutputStream newMaze = new MyCompressorOutputStream(out);
            newMaze.write(mazeToSolve.toByteArray());
            out.flush();
            out.close();

            File[] mazesDirContent = mazesDir.listFiles();
            File maze = null;
            int numOfMazes = mazesDirContent.length;

            //InputStream in1 = new FileInputStream(tempDirectory.getPath() + "/mazeToSolve");
            for (int i = 0; i < numOfMazes; i++) {
                areEqual = true;
                maze = new File(mazesDir.getPath() + "/" + i);


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
                if (areEqual == true) {
                    break;
                }
            }

            if (areEqual == true) {

                ObjectInputStream solutionFile =
                        new ObjectInputStream(new FileInputStream(solutionsDir.getPath() + "/" + maze.getName()));
                solutionToClient = (Solution) solutionFile.readObject();

            } else {

                try {
                    OutputStream addMaze =
                            new FileOutputStream(mazesDir.getPath() + "/" + numOfMazes);
                    MyCompressorOutputStream compressedMaze = new MyCompressorOutputStream(addMaze);
                    compressedMaze.write(mazeToSolve.toByteArray());

                    addMaze.flush();
                    addMaze.close();

                    ObjectOutputStream addSolution =
                            new ObjectOutputStream(new FileOutputStream(solutionsDir.getPath() + "/" + numOfMazes));
                    DepthFirstSearch dfs = new DepthFirstSearch();
                    SearchableMaze searchableMaze = new SearchableMaze(mazeToSolve);
                    solutionToClient = dfs.solve(searchableMaze);
                    addSolution.writeObject(solutionToClient);
                    addSolution.flush();
                    addSolution.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            toClient.writeObject(solutionToClient);
            toClient.flush();
            toClient.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
