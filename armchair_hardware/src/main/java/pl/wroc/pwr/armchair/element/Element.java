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
    private Integer state;
    private Integer maxState;
    private Integer counter;


}
