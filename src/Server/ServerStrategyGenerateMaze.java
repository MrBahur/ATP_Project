package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;

import java.io.*;
import java.util.Objects;
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


    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {

        try {
            fromClient = new ObjectInputStream(inFromClient);
            toClient = new ObjectOutputStream(outToClient);
            toClient.flush();

            mazeDetails = (int[]) fromClient.readObject();
            rows = mazeDetails[0];
            cols = mazeDetails[1];

            IMazeGenerator mazeGenerator = getMazeGenerator();
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
            toClient.flush();
            toClient.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private IMazeGenerator getMazeGenerator() {
        if (Objects.equals(Configurations.getMazeGenerator(), "MyMazeGenerator")) {
            return new MyMazeGenerator();
        } else if (Objects.equals(Configurations.getMazeGenerator(), "SimpleMazeGenerator")) {
            return new SimpleMazeGenerator();
        } else if (Objects.equals(Configurations.getMazeGenerator(), "EmptyMazeGenerator")) {
            return new EmptyMazeGenerator();
        } else return new EmptyMazeGenerator();//default option
    }
}