package Client;

import java.io.IOException;
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
            //server.close();//TODO do i need to close the server in the client? probably not, but i still need to call this method somehow
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}