package Client;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * represents a strategy that the client will use
 */
public interface IClientStrategy {
    void clientStrategy(InputStream inFromServer, OutputStream outToServer);
}
