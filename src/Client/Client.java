package Client;

import java.net.InetAddress;
import java.net.Socket;

public class Client{

    private InetAddress serverIP;
    private int serverPort;
    private IClientStrategy clientStrategy;
    public static int id = 0;

    public Client(InetAddress serverIP, int serverPort, IClientStrategy clientStrategy) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.clientStrategy = clientStrategy;
        this.id = ++id;
    }

    public void communicateWithServer() {

        try {

            System.out.println("client " + id+ " started");
            Socket theServer = new Socket(serverIP, serverPort);

            clientStrategy.clientStrategy(theServer.getInputStream(), theServer.getOutputStream());

            theServer.close();
            System.out.println("client " + this.id+ "  finished");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}