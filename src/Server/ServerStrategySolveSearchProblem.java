package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.search.DepthFirstSearch;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;

import java.io.*;

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    private static String tempDirectoryPath;
    private File mazesDir;
    private File solutionsDir;
    private Maze mazeToSolve;
    private ObjectInputStream fromClient;
    private ObjectOutputStream toClient;
    private Solution solutionToClient;


    public ServerStrategySolveSearchProblem() {


        if(tempDirectoryPath == null) {
            tempDirectoryPath =  System.getProperty("java.io.tmpdir");
            mazesDir= new File(tempDirectoryPath, "Mazes");
            mazesDir.mkdir();
            solutionsDir = new File(tempDirectoryPath, "Solutions");
            solutionsDir.mkdir();
        }
        mazeToSolve = null;
        fromClient = null;
        toClient = null;
        solutionToClient = null;

    }

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {


        try {
            fromClient = new ObjectInputStream(inFromClient);
            toClient = new ObjectOutputStream(outToClient);

            mazeToSolve = (Maze) fromClient.readObject();
            solutionToClient = null;
            boolean areEqual = false;

            FileOutputStream out = new FileOutputStream(tempDirectoryPath + "/mazeToSolve.txt");
            MyCompressorOutputStream newMaze = new MyCompressorOutputStream(out);
            newMaze.write(mazeToSolve.toByteArray());
            out.flush();
            out.close();

            File[] mazesDirContent = mazesDir.listFiles();
            File maze = null;
            int numOfMazes = mazesDirContent.length;

            InputStream in1 = new FileInputStream(tempDirectoryPath + "/mazeToSolve.txt");
            for (int i = 0; i < numOfMazes; i++) {
                areEqual = true;
                maze = mazesDirContent[i];
                InputStream in2 = new FileInputStream(maze);
                while ((in1.available() != 0) || (in2.available() != 0)) {
                    if(in1.available()==0 || in2.available()==0){
                        areEqual = false;
                        break;
                    }
                    if (in1.read() != in2.read()) {
                        areEqual = false;
                        break;
                    }
                }
                if(areEqual == true){
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
