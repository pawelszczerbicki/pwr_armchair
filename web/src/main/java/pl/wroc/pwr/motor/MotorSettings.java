package pl.wroc.pwr.motor;

import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA.
 * User: Pawel
 * Date: 10.11.13
 * Time: 23:35
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
public class MotorSettings {

    private Integer maxPos;
    private Integer minPos;
    private Integer step;

    public MotorSettings(){}

    public MotorSettings(Integer maxPos, Integer minPos, Integer step) {
        this.maxPos = maxPos;
        this.minPos = minPos;
        this.step = step;
    }
}
