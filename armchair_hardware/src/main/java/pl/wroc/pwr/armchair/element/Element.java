package pl.wroc.pwr.armchair.element;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.util.HashMap;
import java.util.Map;

import static pl.wroc.pwr.armchair.element.Direction.BACKWARD;
import static pl.wroc.pwr.armchair.element.Direction.FORWARD;

/**
 * Created by Pawel on 14.01.14.
 */
public class Element {

    private String code;
    private String description;
    private Integer port;
    private Map<Direction, Integer> bits = new HashMap<>();
    private Integer currentState = 0;
    private Integer maxState;
    private Integer counter;

    public Element(String code, Integer port,Integer forwardBit, Integer backwardBit, Integer counter, Integer maxState ) {
        this.code = code;
        this.port = port;
        this.maxState = maxState;
        this.counter = counter;
        bits.put(BACKWARD, backwardBit);
        bits.put(FORWARD, forwardBit);
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

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
