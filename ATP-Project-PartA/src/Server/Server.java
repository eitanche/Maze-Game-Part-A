package Server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class represents a multi-threaded server which receives requests from clients
 */
public class Server {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy strategy;
    private volatile boolean stop;
    private ExecutorService threadPool; // Thread pool


    /**
     * Constructs a Server object
     * @param port The port of the server
     * @param listeningIntervalMS The time to wait for a client
     * @param strategy The strategy the server will use
     */
    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
        this.threadPool = Executors.newFixedThreadPool(Integer.parseInt(Configurations.getConfigInstance().loadProp().getProperty("threadPoolSize")));
    }

    /**
     * Starts the server run in a new thread
     */
    public void start(){

        new Thread(() ->{
            runServer();
        }).start();
    }

    /**
     * The functionality of the server - runs and recieves clients until it's told to stop
     */
    public void runServer(){
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    // Insert the new task into the thread pool:
                    threadPool.submit(() -> {
                        handleClient(clientSocket);
                    });
                } catch (SocketTimeoutException e){
                }
            }
            serverSocket.close();
            threadPool.shutdown(); // do not allow any new tasks into the thread pool (not doing anything to the current tasks and running threads)
        } catch (IOException e) {
        //    LOG.error("IOException", e);
        }
    }

    /**
     * Handles a client which connects to the server, using the strategy
     * @param clientSocket The socket of the client which connected to the server
     */
    private void handleClient(Socket clientSocket) {
        try {
            strategy.ServerStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
      //      LOG.info("Done handling client: " + clientSocket.toString());
            clientSocket.close();
        } catch (IOException e){
  //          LOG.error("IOException", e);

        }
    }

    /**
     * Stops the server run
     */
    public void stop(){
   //     LOG.info("Stopping server...");

        stop = true;
    }
}
