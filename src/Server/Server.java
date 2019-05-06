package Server;

import jdk.management.resource.internal.inst.SocketInputStreamRMHooks;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Server implements Runnable {

    private int port;
    private int listeningIntervalMS;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;
    Socket clientSocket;
    ExecutorService pool;


    public Server(int port, int listeningIntervalMS, IServerStrategy serverStrategy) {

        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.serverStrategy = serverStrategy;
        clientSocket = null;

        //TODO change the number of threads according to the configuration file
        //TODO change the path of the configuration file
        try (InputStream input = new FileInputStream("/Users/Danielle/IdeaProjects/ATP_Project/resources/config.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            int numOfThreads;
            try {
                numOfThreads = Integer.parseInt(prop.getProperty("numOfThreads"));
            } catch (NumberFormatException e) {
                numOfThreads = 0;
            }
            pool = Executors.newFixedThreadPool(numOfThreads);


        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }



    public void start() {

      pool.execute(this);
      pool.shutdown();

    }

    @Override
    public void run() {

        clientSocket = null;

        try {

            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);

            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept(); // blocking call

                    new Thread(() ->
                        serverStrategy(clientSocket)

                    ).start();
//                    try {
//                        Thread.sleep(2000);
//
//                    }catch(Exception e)
//                    {
//                        e.printStackTrace();
//                    }

                } catch (SocketTimeoutException e) {
                    e.getCause();
                }
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private void serverStrategy(Socket clientSocket) {
        try {
            serverStrategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        } catch (IOException e) {
            e.getCause();
        }
    }


    public void stop() {
        stop = true;
    }
}
