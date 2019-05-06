package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;

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

    //TODO change the searching algorithm according to the configuration file

    //TODO change the path of the configuration file
    private ASearchingAlgorithm getSearchingAlgorithm() {

         ASearchingAlgorithm searchingAlgorithm = null;

        try (InputStream input = new FileInputStream("/Users/Danielle/IdeaProjects/ATP_Project/resources/config.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);


            String searchingAlgorithmName = prop.getProperty("searchingAlgorithm");
            if (searchingAlgorithmName.equals("Depth First Search")) {
                searchingAlgorithm = new DepthFirstSearch();
            } else if (searchingAlgorithmName.equals("Breadth First Search")) {
                searchingAlgorithm = new BreadthFirstSearch();
            } else { //searchingAlgorithmName.equals("Best First Search")
                searchingAlgorithm = new BestFirstSearch();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return searchingAlgorithm;
    }



    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {

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

                    SearchableMaze searchableMaze = new SearchableMaze(mazeToSolve);

                    ASearchingAlgorithm  searchingAlgorithm = getSearchingAlgorithm();
                    solutionToClient = searchingAlgorithm.solve(searchableMaze);

                    addSolution.writeObject(solutionToClient);
                    addSolution.flush();
                    addSolution.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            toClient.writeObject(solutionToClient);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}

