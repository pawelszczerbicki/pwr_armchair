package pl.wroc.pwr.armchair.ws;

import pl.wroc.pwr.armchair.driver.Driver;

import static pl.wroc.pwr.armchair.ws.MessageType.*;

/**
 * Created by Pawel on 14.01.14.
 */
public class MessageService {

    private Driver driver = Driver.getInstance();

    public void service(Message m) {
        MessageType type = m.getType();
        if (type == ACTION) {

        } else if (type == CONFIG) {

        }
    }
}
