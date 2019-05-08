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
            System.out.println(String.format("Server started at %s!", serverSocket));
            System.out.println(String.format("Server's Strategy: %S", serverSocket.getClass().getSimpleName()));
            System.out.println("Server is waiting for clients...");

            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    executor.execute(() -> {
                        System.out.println(String.format("Handling client with socket: %s", clientSocket));
                        try {
                            serverStrategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
                            clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (SocketTimeoutException e) {
                    //e.printStackTrace();
                    //System.out.println("waiting for clients");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * method to stop the server
     */
    public void stop() {
        stop = true;

        System.out.println("Stopping server");
        try {
            mainThread.join();
            executor.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}