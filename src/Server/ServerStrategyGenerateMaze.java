package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;
import java.util.ArrayList;

public class ServerStrategyGenerateMaze implements IServerStrategy {

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {

        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);

            int[] mazeSize = (int[]) fromClient.readObject();
            int rows = mazeSize[0];
            int cols = mazeSize[1];
            Maze maze = (new MyMazeGenerator()).generate(rows/*rows*/, cols/*columns*/);
            byte[] mazeBytesArray = maze.toByteArray();
            OutputStream bOut = new ByteArrayOutputStream();
            MyCompressorOutputStream compressMaze = new MyCompressorOutputStream(bOut);
            bOut.flush();
            bOut.close();
            //  MyCompressorOutputStream compressMaze = new MyCompressorOutputStream(new FileOutputStream("tempFile"));
            compressMaze.write(mazeBytesArray);
            //FileInputStream inFile = new FileInputStream("tempFile");
            byte[] mazeRepresentation = new byte[((ByteArrayOutputStream) bOut).size()];
            InputStream bIn = new ByteArrayInputStream(((ByteArrayOutputStream) bOut).toByteArray());
            bIn.read(mazeRepresentation);

            // byte[] mazeRepresentation = new byte[mazeBytesArray.length * 2];
            //  inFile.read(mazeRepresentation);
            toClient.writeObject(mazeRepresentation);
            toClient.flush();
            toClient.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}