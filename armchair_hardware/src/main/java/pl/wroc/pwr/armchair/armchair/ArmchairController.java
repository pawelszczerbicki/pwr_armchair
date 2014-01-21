package pl.wroc.pwr.armchair.armchair;

import static pl.wroc.pwr.armchair.element.Direction.BACKWARD;
import static pl.wroc.pwr.armchair.element.Direction.FORWARD;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.wroc.pwr.armchair.driver.Driver;
import pl.wroc.pwr.armchair.element.Direction;
import pl.wroc.pwr.armchair.element.Element;
import pl.wroc.pwr.armchair.logger.Logger;
import pl.wroc.pwr.armchair.ws.AtmosphereService;
import pl.wroc.pwr.armchair.ws.Message;
import pl.wroc.pwr.armchair.ws.MessageType;
import Automation.BDaq.DioPortDir;

/**
 * Created by Pawel on 02.01.14.
 */
public class ArmchairController {

    private final int READ_COUNTER_DELAY = 500;
    private static ArmchairController instance = new ArmchairController();
    private Driver driver = Driver.getInstance();
    private Map<String, Element> elements = new HashMap<>();
    private AtmosphereService atmosphereService = AtmosphereService.getInstance();
    private Logger logger = Logger.getInstance(getClass(), atmosphereService);

    private ArmchairController() {
    }

    public static ArmchairController getInstance() {
        return instance;
    }


    public void move(String name, int percentageValue) {
        Element e = elements.get(name);
        Integer stepValue = getMoveStep(percentageValue, e);
        if (stepValue == 0) {
            sendMessage(e.getCode(), "MOVING");
            return;
        }
        startMovingAsync(e, stepValue);
        interruptMoving(e, stepValue, driver.getCounterValue(e.getCounter()));
    }

    private void interruptMoving(Element e, int stepValue, int counter) {
        Integer oldCounter;
        Integer currentCounter;
        Integer doneSteps;
        do {
            currentCounter = driver.getCounterValue(e.getCounter());
            doneSteps = currentCounter - counter;
            oldCounter = currentCounter;
            try {
                Thread.sleep(READ_COUNTER_DELAY);
            } catch (InterruptedException ex) {

            }
        } while (shouldGoOnMoving(stepValue, oldCounter, currentCounter, doneSteps));
        stopMovingAndSendMessage(e, doneSteps);
    }

    private boolean shouldGoOnMoving(int stepValue, Integer oldCounter, Integer currentCounter, Integer doneSteps) {
        return doneSteps < Math.abs(stepValue) && oldCounter != currentCounter;
    }

    private void stopMovingAndSendMessage(Element e, Integer doneSteps) {
        driver.setZero(e.getPort());
        e.setCurrentState(doneSteps);
        sendMessage(e.getCode(), doneSteps.toString());
    }

    private int getMoveStep(int percentageValue, Element e) {
        Integer stepValue = e.getMaxState() * percentageValue / 100;
        return stepValue - e.getCurrentState();
    }

    private void startMovingAsync(Element element, int stepValue) {
        Direction d = stepValue > 0 ? FORWARD : BACKWARD;
        driver.sendOneOnSpecificPos(element.getBit(d), element.getPort());
    }

    private void sendMessage(String code, String data) {
        atmosphereService.send(new Message(MessageType.RESPONSE, code, data));
    }

    public void setConfiguration(List<Element> config) {
        elements.clear();
        for (Element c : config) {
            elements.put(c.getCode(), c);
            driver.setPortDirection(c.getPort(), DioPortDir.Output);            
        }
    }
}
