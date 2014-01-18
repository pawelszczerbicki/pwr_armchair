package pl.wroc.pwr.armchair.element;

import java.util.Map;

/**
 * Created by Pawel on 14.01.14.
 */
public class Element {

    private String code;
    private String description;
    private Integer port;
    private Map<Direction, Integer> bits;
    private Integer currentState;
    private Integer maxState;
    private Integer counter;

    public Element(String code, Integer port, Integer maxState, Integer counter, Integer backwardBit, Integer forwardBit) {
        this.code = code;
        this.port = port;
        this.maxState = maxState;
        this.counter = counter;
//        bits.put(BACKWARD);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Map<Direction, Integer> getBits() {
        return bits;
    }

    public void setBits(Map<Direction, Integer> bits) {
        this.bits = bits;
    }

    public Integer getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Integer currentState) {
        this.currentState = currentState;
    }

    public Integer getMaxState() {
        return maxState;
    }

    public void setMaxState(Integer maxState) {
        this.maxState = maxState;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public Integer getBit(Direction d) {
        return bits.get(d);
    }
}
