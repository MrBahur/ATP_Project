package test;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;
import java.util.Arrays;

public class RunCompressDecompressMaze {
    public static void main(String[] args) {
        int previousI = 0;
        int j = 10;
        for (int i = 10; i <= 1000; i += 10) {
            for (; j <= 1000; j += Math.random() * 100) {

                String mazeFileName = "savedMaze.maze";
                AMazeGenerator mazeGenerator = new MyMazeGenerator();
                Maze maze = mazeGenerator.generate(i, j); //Generate new maze
                byte[] b = maze.toByteArray();
                //maze.print();
                //System.out.println();
                try {
// save maze to a file
                    OutputStream out = new MyCompressorOutputStream(new
                            FileOutputStream(mazeFileName));

                    out.write(maze.toByteArray());
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                byte savedMazeBytes[] = new byte[0];
                try {
//read maze from file
                    InputStream in = new MyDecompressorInputStream(new
                            FileInputStream(mazeFileName));
                    savedMazeBytes = new byte[maze.toByteArray().length];
                    in.read(savedMazeBytes);
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Maze loadedMaze = new Maze(savedMazeBytes);
                //loadedMaze.print();
                boolean areMazesEquals =
                        Arrays.equals(loadedMaze.toByteArray(), maze.toByteArray());

                if (!areMazesEquals) {
                    System.out.println(String.format("Mazes (%d,%d) equal: %s", i, j, false));
                    break;
                } else if (i != previousI) {
                    System.out.println(String.format("Still good (%d,%d)", i, j));
                    previousI = i;
                }
//maze should be equal to loadedMaze
            }
            j = (int) (10 + Math.random() * 100);
        }
    }
}