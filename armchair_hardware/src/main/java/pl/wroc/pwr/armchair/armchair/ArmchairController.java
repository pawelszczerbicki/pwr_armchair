package pl.wroc.pwr.armchair.armchair;

import pl.wroc.pwr.armchair.driver.Driver;
import pl.wroc.pwr.armchair.element.Direction;
import pl.wroc.pwr.armchair.element.Element;
import pl.wroc.pwr.armchair.logger.Logger;
import pl.wroc.pwr.armchair.ws.AtmosphereSender;
import pl.wroc.pwr.armchair.ws.Message;
import pl.wroc.pwr.armchair.ws.MessageType;
import pl.wroc.pwr.armchair.ws.SettingsParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static pl.wroc.pwr.armchair.element.Direction.BACKWARD;
import static pl.wroc.pwr.armchair.element.Direction.FORWARD;

/**
 * Created by Pawel on 02.01.14.
 */
public class ArmchairController {

    private final int READ_COUNTER_DELAY = 50;
    private static ArmchairController instance = new ArmchairController();
    private Driver driver = Driver.getInstance();
    private Map<String, Element> elements = new HashMap<>();
    private AtmosphereSender sender = AtmosphereSender.getInstance();
    private Logger logger = Logger.getInstance(getClass(), sender);
    private SettingsParser parser = new SettingsParser();
    private int mockCounterValue = 0;

    private ArmchairController() {
        setConfiguration(parser.getDefaultElements());
    }

    public static ArmchairController getInstance() {
        return instance;
    }


    public void move(String name, int percentageValue) {
        Element e = elements.get(name);
        System.out.println(e);
        Integer stepValue = getMoveStep(percentageValue, e);
        sendMessage(e.getCode(), "MOVING");
        if (stepValue == 0) {
            System.out.println("step value 0");
            sendMessage(e.getCode(), e.getCurrentState().toString());
            return;
        }
        System.out.println("starting moving");
        startMovingAsync(e, stepValue);
        interruptMoving(e, stepValue, mockCounterValue);
    }

    private void interruptMoving(Element e, int stepValue, int counter) {
        Integer oldCounter;
        Integer currentCounter = mockCounterValue;
        Integer doneSteps = 0;
        do {
            mockCounterValue++;
            oldCounter = new Integer(currentCounter);
            currentCounter = new Integer(mockCounterValue);
            doneSteps = currentCounter - counter;
            sendMessage(e.getCode(), String.valueOf(new Double((double) (e.getCurrentState() + doneSteps) / e.getMaxState() * 100).intValue()));
            System.out.println("mock counter: " + mockCounterValue);
            System.out.println("old counter: " + oldCounter);
            System.out.println("done steps: " + doneSteps);
            try {
                Thread.sleep(READ_COUNTER_DELAY);
            } catch (InterruptedException ex) {

            }

        } while (shouldGoOnMoving(stepValue, oldCounter, currentCounter, doneSteps));
        stopMovingAndSendMessage(e, doneSteps);
    }

    private boolean shouldGoOnMoving(int stepValue, Integer oldCounter, Integer currentCounter, Integer doneSteps) {
        System.out.println("go on moving?");
        System.out.println("counters different: " + new Boolean(!oldCounter.equals(currentCounter)).toString());
        System.out.println("Abs value: " + new Boolean(doneSteps < Math.abs(stepValue)).toString());
        return doneSteps < Math.abs(stepValue) && !oldCounter.equals(currentCounter);
    }

    private void stopMovingAndSendMessage(Element e, Integer doneSteps) {
        driver.setZero(e.getPort());
        e.setCurrentState(e.getCurrentState() + doneSteps);
        sendMessage(e.getCode(), String.valueOf(new Double((double) e.getCurrentState() / e.getMaxState() * 100).intValue()));
    }

    private int getMoveStep(int percentageValue, Element e) {
        System.out.println("percentage: " + percentageValue);
        Integer stepValue = new Double(e.getMaxState() * percentageValue / 100).intValue();
        System.out.println("step value:" + stepValue);
        System.out.println("current :" + e.getCurrentState());
        return stepValue - e.getCurrentState();
    }

    private void startMovingAsync(Element element, int stepValue) {
        Direction d = stepValue > 0 ? FORWARD : BACKWARD;
        System.out.println("Direction: " + d);
        driver.sendOneOnSpecificPos(element.getBit(d), element.getPort());
    }

    private void sendMessage(String code, String data) {
        sender.send(new Message(MessageType.RESPONSE, code, data));
    }

    public void setConfiguration(List<Element> config) {
        elements.clear();
        for (Element c : config) {
            elements.put(c.getCode(), c);
            // driver.setPortDirection(c.getPort(), DioPortDir.Output);
        }
    }

    public void calibrate() {
    }
}
