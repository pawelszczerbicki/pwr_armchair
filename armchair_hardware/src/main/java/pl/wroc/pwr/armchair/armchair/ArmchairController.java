package pl.wroc.pwr.armchair.armchair;

import pl.wroc.pwr.armchair.driver.Driver;
import pl.wroc.pwr.armchair.element.Direction;
import pl.wroc.pwr.armchair.element.Element;
import pl.wroc.pwr.armchair.ws.AtmosphereService;
import pl.wroc.pwr.armchair.ws.Message;
import pl.wroc.pwr.armchair.ws.MessageType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pawel on 02.01.14.
 */
public class ArmchairController {
	private static final int READ_COUNTER_DELAY = 500;
	
    private static ArmchairController instance = new ArmchairController();
    private Driver driver = Driver.getInstance();
    private Map<String, Element> elements = new HashMap<>();
    private AtmosphereService atmosphereService = new AtmosphereService();

    private ArmchairController() {
    }

    public static ArmchairController getInstance() {
        return instance;
    }

    public ArmchairController(Map<String, Element> elements) {
        this.elements = elements;
    }

    public void move(String name, int value) {
    	Element element = elements.get(name);
    	
    	int currentState = element.getCurrentState();
    	int maxState = element.getMaxState();
    	
    	int expectedState = maxState * value / 100;
    	
    	int diff = expectedState - currentState;
    	
    	int currentCounter = driver.getCounterValue();
    	if (diff > 0) {
    		driver.sendOneOnSpecificPos(element.getBit(Direction.FORWARD), element.getPort());    		
    	} else {
    		driver.sendOneOnSpecificPos(element.getBit(Direction.BACKWARD), element.getPort());
    	}    	
    	  	
    	int oldCounter = currentCounter;
    	while (true) {
    		int tempCounter = driver.getCounterValue();    		
    		int step = tempCounter - currentCounter;
    		
    		if (step > Math.abs(diff) || oldCounter == tempCounter) {
    			driver.setZero(element.getPort());
    			atmosphereService.send(new Message(MessageType.RESPONSE, "ok"));
    			break;
    		}
    		oldCounter = tempCounter;
    		
    		try {
				Thread.sleep(READ_COUNTER_DELAY);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     	}
    }

    public void setConfiguration(List<Element> config) {
        elements.clear();
        for (Element c : config) {
            elements.put(c.getCode(), c);
        }
    }
}
