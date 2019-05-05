package Client;

import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private InetAddress serverIP;
    private int serverPort;
    private IClientStrategy clientStrategy;

    public Client(InetAddress ip, int port, IClientStrategy clientStrategy) {
        this.serverIP = ip;
        this.serverPort = port;
        this.clientStrategy = clientStrategy;

    }

    public void communicateWithServer() {
        try {
            Socket server = new Socket(serverIP, serverPort);
            System.out.println("Client is connected to server!");
            clientStrategy.clientStrategy(server.getInputStream(),server.getOutputStream());
            server.close();//?
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}