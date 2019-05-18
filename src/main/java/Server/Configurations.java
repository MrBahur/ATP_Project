package Server;

import java.io.*;
import java.util.Properties;

public class Configurations {

    public static void main(String[] args) {

        System.out.println(getMazeGenerator());
        System.out.println(getNumOfThreads());
        System.out.println(getSearchingAlgorithm());
    }

    /**
     * Setter for num of threads in the config file
     * @param num represented the number of threads you want to use in the server
     */

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

    /**
     * Setter for searching algorithm type in the config file
     * @param searchingAlgorithm string represented the type of searching algorithm you want to use
     */
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

    /**
     * Setter for mazeGenerator type in the config file
     * @param mazeGenerator string represented the type of generator you want to use
     */
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

    /**
     * getter for the config of the numOfThreads
     * @return String that represent the numOfThreads that in the config file
     */
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


    /**
     * getter for the config of the searching algorithm
     * @return String that represent the searching algorithm that in the config file
     */
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

    /**
     * getter for the config of the mazeGenerator
     * @return String that represent the mazeGenerator that in the config file
     */
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
