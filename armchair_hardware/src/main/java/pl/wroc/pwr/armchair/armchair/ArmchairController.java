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

import static java.lang.String.format;
import static pl.wroc.pwr.armchair.element.Direction.BACKWARD;
import static pl.wroc.pwr.armchair.element.Direction.FORWARD;
import static pl.wroc.pwr.armchair.ws.MessageType.CALIBRATE;
import static pl.wroc.pwr.armchair.ws.MessageType.RESPONSE;

/**
 * Created by Pawel on 02.01.14.
 */
public class ArmchairController {

    private final int READ_COUNTER_DELAY = 200;
    private static ArmchairController instance = new ArmchairController();
    private Driver driver = Driver.getInstance();
    private Map<String, Element> elements = new HashMap<>();
    private AtmosphereSender sender = AtmosphereSender.getInstance();
    private Logger logger = Logger.getInstance(getClass(), sender);
    private SettingsParser parser = new SettingsParser();
    private final String STOP_MOVING = "STOP_MOVING";
    private final String START_MOVING = "START_MOVING";

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
        System.out.println("Percentage: " + percentageValue);
        System.out.println(e);
        send(RESPONSE, e.getCode(), START_MOVING);
        if (stepValue.equals(0) || e.getCounter() == 0) {
            send(RESPONSE, e.getCode(), e.getCurrentState().toString());
            send(RESPONSE, e.getCode(), STOP_MOVING);
            return;
        }
        System.out.println("Current: " + e.getCurrentState());
        System.out.println("So should set: " + e.getCurrentState() + Math.abs(stepValue));
        startMovingAsync(e, stepValue);
        interruptMoving(e, stepValue, driver.getCounterValue(e.getCounter()));
    }

    private void interruptMoving(Element e, int stepValue, int counter) {
        int oldCounter;
        int currentCounter = counter;
        int doneSteps;
        int currentStatePercentage = getPercentage(e.getCurrentState(), e.getMaxState());
        do {
            try {
                Thread.sleep(READ_COUNTER_DELAY);
            } catch (InterruptedException ex) {

            }
            oldCounter = currentCounter;
            currentCounter = driver.getCounterValue(e.getCounter());
            System.out.println(format("counter currend:%s, old: %s", currentCounter, oldCounter));
//            currentCounter = mockCounterValue;
            doneSteps = stepValue > 0 ? currentCounter - counter : -(currentCounter - counter);
            currentStatePercentage = sendAndReturnCurrentState(e, doneSteps, currentStatePercentage);
            System.out.println(format("Done steps: %s, next percentage: %s, current: %s", doneSteps, (e.getCurrentState() + doneSteps) * 100 / e.getMaxState(), currentStatePercentage));

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
            moveCalibrate(s, 0);
        }
        send(CALIBRATE, null, "STOP");
    }

    private void moveCalibrate(String s, int i) {
        Element e = elements.get(s);
        send(RESPONSE, e.getCode(), START_MOVING);

        System.out.println("Current: " + e.getCurrentState());
        startMovingAsync(e, -1);
        send(RESPONSE, e.getCode(), START_MOVING);
        try {
            interruptCalibration(e);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        send(RESPONSE, e.getCode(), "0");
        e.setCurrentState(0);
        elements.put(e.getCode(), e);
        send(RESPONSE, e.getCode(), STOP_MOVING);
    }

    private void interruptCalibration(Element e) throws InterruptedException {
        System.out.println(e);
        int counter = driver.getCounterValue(e.getCounter());
        int oldCounter;
        do {
            if (e.getCounter() == 0)
                break;
            oldCounter = counter;
            Thread.sleep(READ_COUNTER_DELAY);
            counter = driver.getCounterValue(e.getCounter());
            System.out.println(format("old counter: %s, current counter: %s", oldCounter, counter));

        } while (oldCounter != counter);
        driver.setZero(e.getPort());
    }
}
