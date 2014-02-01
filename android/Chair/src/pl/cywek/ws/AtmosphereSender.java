package pl.cywek.ws;

import org.atmosphere.wasync.Socket;

import android.util.Log;

import java.io.IOException;

public class AtmosphereSender {
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
        	Log.d("chair", "Error sending message");
        }
        
    }
    
    public void closeSocket() {
    	socket.close();
    }
    
}
