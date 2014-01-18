package pl.wroc.pwr.armchair.ws;

import pl.wroc.pwr.armchair.armchair.ArmchairController;

import static pl.wroc.pwr.armchair.ws.MessageType.ACTION;
import static pl.wroc.pwr.armchair.ws.MessageType.CONFIG;

/**
 * Created by Pawel on 14.01.14.
 */
public class MessageService {
    private ArmchairController controller = ArmchairController.getInstance();
    private SettingsParser parser = new SettingsParser();

    public void service(Message m) {
        MessageType type = m.getType();
        if (type == ACTION) {
            controller.move(m.getCode(), Integer.parseInt(m.getData()));
        } else if (type == CONFIG) {
            controller.setConfiguration(parser.getElements(m.getData()));
        }
    }
}
