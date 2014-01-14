package pl.wroc.pwr.armchair.ws;

import pl.wroc.pwr.armchair.element.Direction;

/**
 * Created by Pawel on 14.01.14.
 */
public class Message {

    private MessageType type;
    private String  code;
    private Direction direction;
    private String data;

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
