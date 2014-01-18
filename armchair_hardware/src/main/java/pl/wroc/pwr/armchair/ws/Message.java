package pl.wroc.pwr.armchair.ws;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * Created by Pawel on 14.01.14.
 */
public class Message {

    private MessageType type;
    private String  code;
    private String data;

    public Message(MessageType type, String data) {
        this.type = type;
        this.data = data;
    }

    public Message() {
    }

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
