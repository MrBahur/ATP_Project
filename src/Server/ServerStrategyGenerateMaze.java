package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.EmptyMazeGenerator;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;

import java.io.*;
import java.util.Objects;

public class ServerStrategyGenerateMaze implements IServerStrategy {
    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();

            int[] dimensions = (int[]) fromClient.readObject();
            byte[] notCompressedByteArray = (getMazeGenerator()).generate(dimensions[0], dimensions[1]).toByteArray();
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            MyCompressorOutputStream compressor = new MyCompressorOutputStream(b);
            compressor.write(notCompressedByteArray);
            toClient.writeObject(b.toByteArray());
            //toClient.writeObject();

        } catch (Exception e) {
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