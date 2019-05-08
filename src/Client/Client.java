package Client;

import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class Client{

    private InetAddress serverIP;
    private int serverPort;
    private IClientStrategy clientStrategy;
    private static int id = 0;

    public Client(InetAddress serverIP, int serverPort, IClientStrategy clientStrategy) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.clientStrategy = clientStrategy;
    }

    public void communicateWithServer() {
        try {
            Socket theServer = new Socket(serverIP, serverPort);
            clientStrategy.clientStrategy(theServer.getInputStream(), theServer.getOutputStream());
            theServer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}