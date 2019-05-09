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
            Socket server = new Socket(serverIP, serverPort);
            clientStrategy.clientStrategy(server.getInputStream(), server.getOutputStream());
            server.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}