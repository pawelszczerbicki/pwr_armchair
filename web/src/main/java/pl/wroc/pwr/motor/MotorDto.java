package pl.wroc.pwr.motor;

import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA.
 * User: Pawel
 * Date: 10.11.13
 * Time: 23:30
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
public class MotorDto {

    private String id;
    private Double value;

    public MotorDto(String id, Double value) {
        this.id = id;
        this.value = value;
    }
}
