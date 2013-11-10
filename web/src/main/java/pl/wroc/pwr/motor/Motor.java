package pl.wroc.pwr.motor;

import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA.
 * User: Pawel
 * Date: 10.11.13
 * Time: 23:28
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
public class Motor {

    private String id;
    private String name;
    private Double value;
    private MotorSettings settings;

    public Motor() {
    }

    public Motor(String id, String name, Double value, MotorSettings settings) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.settings = settings;
    }
}
