package Server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;
    private Thread mainThread;
    private ThreadPoolExecutor executor;


    public Server(int port, int listeningIntervalMS, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.serverStrategy = serverStrategy;
        mainThread = new Thread(this::runServer);
        executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        executor.setCorePoolSize(10);
        executor.setMaximumPoolSize(10);
    }

    public void start() {
        mainThread.start();
        Scanner s = new Scanner(System.in);
        do {
            System.out.print(">>");
        } while (!Objects.equals(s.next().toLowerCase(), "exit"));
        stop = true;
    }

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
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (executor.getActiveCount() == 0) {
            executor.shutdown();
            stop = true;
            System.out.println("Stopping server");
            try {
                mainThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}