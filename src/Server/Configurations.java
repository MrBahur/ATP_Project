package Server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class Configurations {

    public static void main(String[] args) {

        //TODO change the path of the configuration file
        try (OutputStream output = new FileOutputStream("/Users/Danielle/IdeaProjects/ATP_Project/resources/config.properties")) {

            Properties prop = new Properties();

            //TODO how does the user inserts the values?
            // set the properties value
            prop.setProperty("numOfThreads", "5");
            prop.setProperty("searchingAlgorithm", "Depth First Search");
            prop.setProperty("MazeGenerator", "MyMazeGenerator");

            // save properties to project root folder
            prop.store(output, null);


        } catch (IOException io) {
            io.printStackTrace();
        }

    }

}
