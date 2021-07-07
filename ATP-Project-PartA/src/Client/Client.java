package Client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * This class represents a client that connects to the server
 */
public class Client {
    private InetAddress serverIP;
    private int serverPort;
    private IClientStrategy strategy;

    /**
     * Constructor of the client class
     * @param serverIP The address of the server to connect
     * @param serverPort The port of the server to connect
     * @param strategy The strategy that the client will use
     */
    public Client(InetAddress serverIP, int serverPort, IClientStrategy strategy) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.strategy = strategy;
    }

    /**
     * Starts communication with the server, using the strategy
     */
    public void communicateWithServer(){
        try(Socket serverSocket = new Socket(serverIP, serverPort)){
            strategy.clientStrategy(serverSocket.getInputStream(), serverSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
