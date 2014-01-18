package pl.wroc.pwr.armchair.armchair;

import pl.wroc.pwr.armchair.driver.Driver;
import pl.wroc.pwr.armchair.element.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pawel on 02.01.14.
 */
public class ArmchairController {
    private static ArmchairController instance = new ArmchairController();
    private Driver driver = Driver.getInstance();
    private Map<String, Element> elements = new HashMap<>();

    private ArmchairController() {
    }

    public static ArmchairController getInstance() {
        return instance;
    }

    public ArmchairController(Map<String, Element> elements) {
        this.elements = elements;
    }

    public void move() {

    }

    public void setConfiguration(List<Element> config) {
        elements.clear();
        for (Element c : config) {
            elements.put(c.getCode(), c);
        }
    }
}
