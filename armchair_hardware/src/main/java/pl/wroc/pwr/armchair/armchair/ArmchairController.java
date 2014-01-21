package pl.wroc.pwr.armchair.armchair;

import Automation.BDaq.DioPortDir;
import pl.wroc.pwr.armchair.driver.Driver;
import pl.wroc.pwr.armchair.element.Direction;
import pl.wroc.pwr.armchair.element.Element;
import pl.wroc.pwr.armchair.logger.Logger;
import pl.wroc.pwr.armchair.ws.AtmosphereSender;
import pl.wroc.pwr.armchair.ws.Message;
import pl.wroc.pwr.armchair.ws.MessageType;
import pl.wroc.pwr.armchair.ws.SettingsParser;

import java.util.*;

import static pl.wroc.pwr.armchair.element.Direction.BACKWARD;
import static pl.wroc.pwr.armchair.element.Direction.FORWARD;
import static pl.wroc.pwr.armchair.ws.MessageType.CALIBRATE;
import static pl.wroc.pwr.armchair.ws.MessageType.RESPONSE;

/**
 * Created by Pawel on 02.01.14.
 */
public class ArmchairController {

    private final int READ_COUNTER_DELAY = 5;
    private static ArmchairController instance = new ArmchairController();
    private Driver driver = Driver.getInstance();
    private Map<String, Element> elements = new HashMap<>();
    private AtmosphereSender sender = AtmosphereSender.getInstance();
    private Logger logger = Logger.getInstance(getClass(), sender);
    private SettingsParser parser = new SettingsParser();
    private final String STOP_MOVING = "STOP_MOVING";
    private final String START_MOVING = "START_MOVING";
    private int mockCounterValue = 0;

    private ArmchairController() {
        setConfiguration(parser.getDefaultElements());
    }

    public static ArmchairController getInstance() {
        return instance;
    }


    public void move(String name, int percentageValue) {
        Element e = elements.get(name);
        Integer stepValue = getMoveStep(percentageValue, e);
        System.out.println("Step value: " + stepValue);
        send(RESPONSE, e.getCode(), START_MOVING);
        if (stepValue.equals(0)) {
            send(RESPONSE, e.getCode(), e.getCurrentState().toString());
            send(RESPONSE, e.getCode(), STOP_MOVING);
            return;
        }
        System.out.println("Current: " + e.getCurrentState());
        System.out.println("So should set: " + e.getCurrentState() + Math.abs(stepValue));
        startMovingAsync(e, stepValue);
        interruptMoving(e, stepValue, mockCounterValue);
    }

    private void interruptMoving(Element e, int stepValue, int counter) {
        int oldCounter;
        int currentCounter = mockCounterValue;
        int doneSteps;
        int currentStatePercentage = getPercentage(e.getCurrentState(), e.getMaxState());
        do {
            mockCounterValue++;
            oldCounter = currentCounter;
            currentCounter = mockCounterValue;
            doneSteps = stepValue > 0 ? currentCounter - counter : -(currentCounter - counter);
            System.out.println(String.format("Done steps: %s, next percentage: %s, current: %s", doneSteps, (e.getCurrentState() + doneSteps) * 100 / e.getMaxState(), currentStatePercentage));
            currentStatePercentage = sendAndReturnCurrentState(e, doneSteps, currentStatePercentage);
            try {
                Thread.sleep(READ_COUNTER_DELAY);
            } catch (InterruptedException ex) {

            }
        } while (shouldGoOnMoving(stepValue, oldCounter, currentCounter, doneSteps));
        stopMovingAndSendMessage(e, doneSteps);
    }

    private int getPercentage(int value, int max) {
        return value * 100 / max;
    }

    private int sendAndReturnCurrentState(Element e, int doneSteps, int currentStatePercentage) {
        int newState = getPercentage(e.getCurrentState() + doneSteps, e.getMaxState());
        if (newState != currentStatePercentage)
            send(RESPONSE, e.getCode(), String.valueOf(newState));
        return newState;
    }

    private boolean shouldGoOnMoving(int stepValue, int oldCounter, int currentCounter, int doneSteps) {
        return Math.abs(doneSteps) < Math.abs(stepValue) && oldCounter != currentCounter;
    }

    private void stopMovingAndSendMessage(Element e, Integer doneSteps) {
        driver.setZero(e.getPort());
        e.setCurrentState(e.getCurrentState() + doneSteps);
        send(RESPONSE, e.getCode(), String.valueOf(new Double((double) e.getCurrentState() / e.getMaxState() * 100).intValue()));
        send(RESPONSE, e.getCode(), STOP_MOVING);
    }

    private int getMoveStep(int percentageValue, Element e) {
        Integer stepValue = e.getMaxState() * percentageValue / 100;
        return stepValue - e.getCurrentState();
    }

    private void startMovingAsync(Element element, int stepValue) {
        Direction d = stepValue > 0 ? FORWARD : BACKWARD;
        System.out.println("Direction: " + d);
        driver.sendOneOnSpecificPos(element.getPort(), element.getBit(d));
    }

    private void send(MessageType type, String code, String data) {
        sender.send(new Message(type, code, data));
    }

    public void setConfiguration(List<Element> config) {
        elements.clear();
        Set<Integer> ports = new HashSet<>();
        for (Element c : config) {
            elements.put(c.getCode(), c);
            ports.add(c.getPort());

        }
        for (Integer port : ports) {
            driver.setPortDirection(port, DioPortDir.Output);
        }
    }

    //TODO make calibration based only on counter
    //TODO add calibration and zeros in other methods,
    //TODO calibration should get max values, and zeros should set chair to zero
    public void calibrate() {
        send(CALIBRATE, null, "START");
        for (String s : elements.keySet()) {
            move(s, 0);
        }
        send(CALIBRATE, null, "STOP");
    }
}
