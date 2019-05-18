package test;

import algorithms.mazeGenerators.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class myTest {
    private static final Logger logger1 = LogManager.getLogger("GeneratorLogger");
    private static final Logger logger2 = LogManager.getLogger("SolverLogger");
    public static void main(String[] args) {
        //testMazeGenerator(new EmptyMazeGenerator());
        //testMazeGenerator(new SimpleMazeGenerator());
        //testMazeGenerator(new MyMazeGenerator());
        //testTextSegmentation();
        //System.out.println(zivFunction("bb", "b+b"));
        testLog();
    }

    private static void testLog() {
        logger1.debug("test debug");
        logger1.info("test info");
        logger1.warn("test warn");
        logger1.error("test error");
        logger1.fatal("test fatal");
        logger2.debug("test debug");
        logger2.info("test info");
        logger2.warn("test warn");
        logger2.error("test error");
        logger2.fatal("test fatal");
    }


    private static void testMazeGenerator(IMazeGenerator mazeGenerator) {
        // prints the time it takes the algorithm to run
        System.out.println(String.format("Maze generation time(ms): %s",
                mazeGenerator.measureAlgorithmTimeMillis(8/*rows*/, 100/*columns*/)));
        // generate another maze
        Maze maze = mazeGenerator.generate(73/*rows*/, 15/*columns*/);
        byte[] b = maze.toByteArray();

        // prints the maze
        maze.print();
        System.out.println();
        Maze maze2 = new Maze(b);
        maze2.print();
        // get the maze entrance
        Position startPosition = maze.getStartPosition();
        // print the position
        System.out.println(String.format("Start Position: %s", startPosition)); // format "{row,column}"
        // prints the maze exit position
        System.out.println(String.format("Goal Position: %s", maze.getGoalPosition()));
    }

    private static void testTextSegmentation() {
        HashMap<Integer, Integer> histogram = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            histogram.put(i, 0);
        }
        InputStream is = null;
        try {
            is = new FileInputStream("savedMaze.maze");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (is != null) {
                int c = is.read();
                while (c != -1) {
                    histogram.replace(c, histogram.get(c), histogram.get(c) + 1);
                    c = is.read();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 256; i++) {
            System.out.println(i + ": " + histogram.get(i));
        }
    }

//    private static void zivFunction1(String search) {//assuming word and search contains only letters and search can contains + and *
//        for (int i = 0; i < search.length(); i++) {//changing the + to x*
//            if (search.charAt(i) == '+') {
//                char x = search.charAt(i - 1);
//                search = search.substring(0, i) + x + '*' + search.substring(i + 1);
//            }
//        }
//    }
//
//    private static boolean zivFunction2(String word, String search){
//        boolean option1;
//        if(search.charAt(1) == '*'){
//            option1 = zivFunction2(word,search.substring(1));
//        }
//    }
}
