package Server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.OutputStream;

public interface IServerStrategy {

    void serverStrategy(InputStream inFromClient, OutputStream outToClient);

    Logger getLogger();

}