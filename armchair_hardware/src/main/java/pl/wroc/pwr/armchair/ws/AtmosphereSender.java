package pl.wroc.pwr.armchair.ws;

import org.apache.log4j.Logger;
import org.atmosphere.wasync.Socket;

import java.io.IOException;

/**
 * Created by Pawel on 21.01.14.
 */
public class AtmosphereSender {
    private final Logger logger = Logger.getLogger(getClass());
    private static AtmosphereSender instance = new AtmosphereSender();
    private Socket socket;

    public static AtmosphereSender getInstance() {
        return instance;
    }

    private AtmosphereSender() {
    }

    public void setSocket(Socket s) {
        this.socket = s;
    }

    public void send(Message m) {
        if (socket == null)
            throw new IllegalStateException("No socket provided");
        try {
            socket.fire(m);
        } catch (IOException e) {
            logger.error("Error sending message");
        }
    }
}
