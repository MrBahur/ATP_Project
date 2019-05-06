package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;
import java.util.Arrays;

public class ServerStrategyGenerateMaze implements IServerStrategy {
    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try{
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();

            int[] dimensions = (int[])fromClient.readObject();
            byte[] notCompressedByteArray = (new MyMazeGenerator()).generate(dimensions[0],dimensions[1]).toByteArray();
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            MyCompressorOutputStream compressor = new MyCompressorOutputStream(b);
            compressor.write(notCompressedByteArray);
            toClient.writeObject(b.toByteArray());
            //toClient.writeObject();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}