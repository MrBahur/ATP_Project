package Server;

import java.io.*;
import java.util.Properties;

public class Configurations {

    public static void main(String[] args) {
        
            System.out.println(getMazeGenerator());
            System.out.println(getNumOfThreads());
            System.out.println(getSearchingAlgorithm());
    }

    public static void setNumOfThreads(int num) {
        try {
            Properties p = new Properties();
            p.load(new FileInputStream("./resources/config.properties"));

            OutputStream out = new FileOutputStream("./resources/config.properties");

            p.setProperty("numOfThreads", Integer.toString(num));

            p.store(out, null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setSearchingAlgorithm(String searchingAlgorithm) {
        try {
            Properties p = new Properties();
            p.load(new FileInputStream("./resources/config.properties"));

            OutputStream out = new FileOutputStream("./resources/config.properties");

            p.setProperty("searchingAlgorithm", searchingAlgorithm);

            p.store(out, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setMazeGenerator(String mazeGenerator) {
        try {
            Properties p = new Properties();
            p.load(new FileInputStream("./resources/config.properties"));

            OutputStream out = new FileOutputStream("./resources/config.properties");

            p.setProperty("MazeGenerator", mazeGenerator);

            p.store(out, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getNumOfThreads() {
        try (InputStream input = new FileInputStream("./resources/config.properties")) {

            Properties p = new Properties();

            p.load(input);

            int numOfThreads;
            try {
                numOfThreads = Integer.parseInt(p.getProperty("numOfThreads"));
            } catch (NumberFormatException e) {
                numOfThreads = 1;
            }
            return numOfThreads;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static String getSearchingAlgorithm() {
        try (InputStream input = new FileInputStream("./resources/config.properties")) {

            Properties p = new Properties();

            p.load(input);

            return p.getProperty("searchingAlgorithm");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMazeGenerator() {
        try (InputStream input = new FileInputStream("./resources/config.properties")) {

            Properties p = new Properties();

            p.load(input);

            return p.getProperty("MazeGenerator");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
