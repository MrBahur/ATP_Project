package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private int port;
    private int listeningIntervalMS;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;
    Socket clientSocket;
    //ExecutorService pool;


    public Server(int port, int listeningIntervalMS, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.serverStrategy = serverStrategy;
        clientSocket = null;
        // pool = Executors.newFixedThreadPool(5);

    }


    public void start() {

        new Thread(() ->
            runServer()
        ).start();

    }

    //TODO threadPool
    public void runServer() {

        clientSocket = null;

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            // serverSocket.setSoTimeout(listeningIntervalMS);

            while (!stop) {
                try {
                    clientSocket = serverSocket.accept(); // Accepts client
                    new Thread(() ->
                        serverStrategy(clientSocket)
                    ).start();

                } catch (SocketTimeoutException e) {
                    e.printStackTrace();
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
            e.printStackTrace();
        }
    }


    public void stop() {
        stop = true;
    }
}