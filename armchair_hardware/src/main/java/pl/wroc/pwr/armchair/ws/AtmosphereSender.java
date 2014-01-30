package pl.wroc.pwr.armchair.ws;

import org.apache.log4j.Logger;
import org.atmosphere.wasync.Socket;

import java.io.IOException;

/**
 * Created by Pawel on 21.01.14.
 */
public class AtmosphereSender {
    private final Logger logger = Logger.getLogger(getClass());
    private Socket socket;

    public AtmosphereSender(Socket socket) {
        this.socket = socket;
    }

    public void send(Message m) {
        if (socket == null) {
            logger.info("Socket not set!");
            return;
        }
        try {
            socket.fire(m);
        } catch (IOException e) {
            logger.error("Error sending message");
        }
    }
}
