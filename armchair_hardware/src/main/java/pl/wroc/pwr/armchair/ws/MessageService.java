package pl.wroc.pwr.armchair.ws;

import org.apache.log4j.Logger;
import pl.wroc.pwr.armchair.armchair.ArmchairController;

import static pl.wroc.pwr.armchair.ws.MessageType.*;

/**
 * Created by Pawel on 14.01.14.
 */
public class MessageService {
    private ArmchairController controller;
    private SettingsParser parser = new SettingsParser();
    private Logger logger = Logger.getLogger(getClass());

    public MessageService(ArmchairController controller) {
        this.controller = controller;
    }

    public void service(Message m) {
        MessageType type = m.getType();
        if (type == ACTION) {
            logger.info("Servicing: " + type);
            controller.move(m.getCode(), Integer.parseInt(m.getData()));
        } else if (type == CONFIG) {
            logger.info("Servicing: " + type);

            if (m.getData().equalsIgnoreCase("true") || m.getData().equalsIgnoreCase("false"))
                controller.setMocked(Boolean.parseBoolean(m.getData()));
        } else if (type == CALIBRATE && (m.getData() == null || m.getData().isEmpty())) {
            logger.info("Servicing: " + type);
            controller.calibrate();
        }
    }
}
