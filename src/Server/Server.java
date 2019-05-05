package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;


    public Server(int port, int listeningIntervalMS, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.serverStrategy = serverStrategy;
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
            System.out.println(String.format("Server started at %s!", serverSocket));
            System.out.println(String.format("Server's Strategy: %S", serverSocket.getClass().getSimpleName()));
            System.out.println("Server is waiting for clients...");
            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println(String.format("Client excepted: %s",clientSocket));
                    try{
                        serverStrategy.serverStrategy(clientSocket.getInputStream(),clientSocket.getOutputStream());
                        clientSocket.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                } catch (SocketTimeoutException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        System.out.println("Stopping server");
        stop = true;
    }
}