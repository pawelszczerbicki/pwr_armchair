package pl.wroc.pwr.armchair.armchair;

import pl.wroc.pwr.armchair.driver.Driver;
import pl.wroc.pwr.armchair.element.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pawel on 02.01.14.
 */
public class ArmchairController {

    private Driver driver = Driver.getInstance();
    private Map<String, Element> elements = new HashMap<>();


    public ArmchairController(Map<String, Element> elements) {
        this.elements = elements;
    }
}
