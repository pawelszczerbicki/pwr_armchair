package pl.wroc.pwr.armchair;

import pl.wroc.pwr.armchair.ws.AtmosphereSender;
import pl.wroc.pwr.armchair.ws.Message;
import pl.wroc.pwr.armchair.ws.MessageType;

import java.util.TimerTask;

/**
 * Created by Pawel on 25.01.14.
 */
public class Heartbeat extends TimerTask{
    private AtmosphereSender sender;

    public Heartbeat(AtmosphereSender sender) {
        this.sender = sender;
    }

    @Override
    public void run() {
        sender.send(new Message(MessageType.HEARTBEAT, null));
    }
}
