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
import static pl.wroc.pwr.armchair.ws.MessageType.CONFIG;
import static pl.wroc.pwr.armchair.ws.MessageType.RESPONSE;

/**
 * Created by Pawel on 02.01.14.
 */
public class ArmchairController {

    private final int READ_COUNTER_DELAY = 200;
    private Driver driver;
    private Map<String, Element> elements = new HashMap<>();
    private AtmosphereSender sender;
    private Logger logger;
    private SettingsParser parser = new SettingsParser();
    private final String STOP_MOVING = "STOP_MOVING";
    private final String START_MOVING = "START_MOVING";
    private Integer mockCounter = 0;
    private Integer mockCallibration = 0;
    private Boolean mocked = false;

    public ArmchairController(AtmosphereSender sender, Boolean mocked) {
        this.mocked = mocked;
        this.sender = sender;
        this.logger = Logger.getInstance(getClass(), sender);
        this.driver = new Driver(sender);
        setConfiguration(parser.getDefaultElements());
    }

    public void move(String name, int percentageValue) {
        Element e = elements.get(name);
        Integer stepValue = getMoveStep(percentageValue, e);
        send(RESPONSE, e.getCode(), START_MOVING);
        //TODO in case of wrong counter1 || e.getCounter() == 0
        if (stepValue.equals(0)) {
            send(RESPONSE, e.getCode(), e.getCurrentState().toString());
            send(RESPONSE, e.getCode(), STOP_MOVING);
            return;
        }
        startMovingAsync(e, stepValue);
        interruptMoving(e, stepValue, getCounterValue(e));
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
                logger.error("Can not wait for moving, continue");
            }
            oldCounter = currentCounter;
            currentCounter = getCounterValue(e);
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
        int counter = getCounterValue(e);
        int oldCounter;
        do {
            oldCounter = counter;
            Thread.sleep(READ_COUNTER_DELAY);
            counter = getCounterValue(e);

        } while (keepCalibrating(counter, oldCounter));
        driver.setZero(e.getPort());
    }

    private boolean keepCalibrating(int counter, int oldCounter) {
        if (!mocked) return oldCounter != counter;
        if (mockCallibration >= 10) {
            mockCallibration = 0;
            return false;
        }
        mockCallibration++;
        return true;
    }

    private int getCounterValue(Element e) {
        System.out.println("mocked : " + mocked);
        if (!mocked) return driver.getCounterValue(e.getCounter());
        System.out.println("returning mocked");
        mockCounter += 15;
        return mockCounter;
    }

    public void setMocked(Boolean mocked) {
        this.mocked = mocked;
        String message = mocked ? "MOCKED" : "UNMOCKED";
        sender.send(new Message(CONFIG, message));
    }
}
