package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy {
    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try{
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();

            int[] dimensions = (int[])fromClient.readObject();
            int rows = dimensions[0];
            int cols = dimensions[1];

            MyCompressorOutputStream compressor = new MyCompressorOutputStream(toClient);
            compressor.write(new MyMazeGenerator().generate(rows,cols).toByteArray());

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}