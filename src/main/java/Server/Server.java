package Server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.*;

public class Server {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;
    private Thread mainThread;
    private ExecutorService executor;


    public Server(int port, int listeningIntervalMS, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.serverStrategy = serverStrategy;
        mainThread = new Thread(this::runServer);
        executor = new ThreadPoolExecutor(Configurations.getNumOfThreads(), Configurations.getNumOfThreads(), 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    }

    /**
     * the start method for the server
     */
    public void start() {
        mainThread.start();
//        new Thread(() -> {
//            Scanner s = new Scanner(System.in);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            do {
//                System.out.print(">>");
//            } while (!Objects.equals(s.next().toLowerCase(), "exit"));
//            stop = true;
//            stop();
//        }).start();
    }

    /**
     * the run server method
     */
    private void runServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
            serverStrategy.getLogger().info(String.format("Server started at %s!", serverSocket));
            serverStrategy.getLogger().info(String.format("Server's Strategy: %s", serverStrategy));
            serverStrategy.getLogger().info("Server is waiting for clients...");

            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    executor.execute(() -> {
                        serverStrategy.getLogger().info(String.format("Handling client with socket: %s", clientSocket));
                        try {
                            serverStrategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
                            clientSocket.close();
                        } catch (IOException e) {
                            serverStrategy.getLogger().info(e.toString());
                        }
                    });
                } catch (SocketTimeoutException e) {
                    serverStrategy.getLogger().info(String.format("%s is waiting for clients", this.serverStrategy));
                    serverStrategy.getLogger().info(e.toString());
                }
            }
        } catch (IOException e) {
            serverStrategy.getLogger().info(e.toString());
        }

    }

    /**
     * method to stop the server
     */
    public void stop() {
        stop = true;
        serverStrategy.getLogger().warn(String.format("closing %s", this.serverStrategy));
        try {
            mainThread.join();
            executor.shutdown();
        } catch (InterruptedException e) {
            serverStrategy.getLogger().info(e.toString());
        }
    }
}