package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;

import java.io.*;
import java.util.Properties;


public class ServerStrategyGenerateMaze implements IServerStrategy {

    private ObjectInputStream fromClient;
    private ObjectOutputStream toClient;
    private int[] mazeDetails;
    private int rows;
    private int cols;
    private Maze maze;
    private byte[] mazeBytesArray;
    private OutputStream bOut;
    private MyCompressorOutputStream compressMaze;

    public ServerStrategyGenerateMaze() {

        fromClient = null;
        toClient = null;
        mazeDetails = null;
        rows = 0;
        cols = 0;
        maze = null;
        mazeBytesArray = null;
        bOut = null;
        compressMaze = null;

    }

    //TODO change the maze generator according to the configuration file
    //TODO change the path of the configuration file
    private AMazeGenerator getMazeGenerator(){

        AMazeGenerator mazeGenerator = null;

        try (InputStream input = new FileInputStream("/Users/Danielle/IdeaProjects/ATP_Project/resources/config.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            String mazeGeneratorName = prop.getProperty("MazeGenerator");

            if (mazeGeneratorName.equals("MyMazeGenerator")) {
                mazeGenerator = new MyMazeGenerator();
            } else if (mazeGeneratorName.equals("SimpleMazeGenerator")) {
                mazeGenerator = new SimpleMazeGenerator();
            } else { //mazeGeneratorName.equals("EmptyMazeGenerator")
                mazeGenerator = new EmptyMazeGenerator();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return mazeGenerator;
    }

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {

        try {
            fromClient = new ObjectInputStream(inFromClient);
            toClient = new ObjectOutputStream(outToClient);
            toClient.flush();

            mazeDetails = (int[]) fromClient.readObject();
            rows = mazeDetails[0];
            cols = mazeDetails[1];

            AMazeGenerator mazeGenerator = getMazeGenerator();
            maze = (mazeGenerator.generate(rows/*rows*/, cols/*columns*/));
            //Generates a new Maze according to the values given from the user

            mazeBytesArray = maze.toByteArray();
            bOut = new ByteArrayOutputStream();
            compressMaze = new MyCompressorOutputStream(bOut);
            bOut.flush();
            bOut.close();
            compressMaze.write(mazeBytesArray);
            //Compresses the maze and writes it in the ByteArrayOutputStream

            toClient.writeObject(((ByteArrayOutputStream) bOut).toByteArray());
            //Writes the data from bOut to the client OutputStream


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}