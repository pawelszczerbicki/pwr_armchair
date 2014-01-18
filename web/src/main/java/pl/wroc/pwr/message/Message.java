package pl.wroc.pwr.message;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: Pawel
 * Date: 24.10.13
 * Time: 22:38
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
@ToString
@XmlRootElement
public class Message {

    private MessageType type;
    private String  code;
    private Direction direction;
    private String data;

    public Message(MessageType heartbeat) {

    }

    public Message() {
    }
}
