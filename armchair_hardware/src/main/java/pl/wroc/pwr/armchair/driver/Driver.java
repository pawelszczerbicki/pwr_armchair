package pl.wroc.pwr.armchair.driver;

import Automation.BDaq.*;
import pl.wroc.pwr.armchair.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import static pl.wroc.pwr.armchair.driver.DriverUtils.hasError;

/**
 * Created by Pawel on 02.01.14.
 */
public class Driver {

    private static final Driver d = new Driver();
    private Integer portAmount = 0;
    private Logger logger = Logger.getInstance(getClass());
    private InstantDiCtrl instantDiCtrl = new InstantDiCtrl();
    private InstantDoCtrl instantDoCtrl = new InstantDoCtrl();
    private TimerPulseCtrl timerPulseCtrl = new TimerPulseCtrl();
    private EventCounterCtrl counterCtrl0 = new EventCounterCtrl();
    private EventCounterCtrl counterCtrl1 = new EventCounterCtrl();
    private ArrayList<DeviceTreeNode> installedDevices;

    private Driver() {
        installedDevices = instantDiCtrl.getSupportedDevices();
        selectDevice(0);
    }

    public static Driver getInstance() {
        return d;
    }

    public List<DeviceTreeNode> getConnectedDevices() {
        return installedDevices;
    }

    public void selectDevice(Integer d) {
        try {
            DeviceInformation deviceInformation = new DeviceInformation(
                    installedDevices.get(d).toString());
            instantDiCtrl.setSelectedDevice(deviceInformation);
            instantDoCtrl.setSelectedDevice(deviceInformation);
            timerPulseCtrl.setSelectedDevice(deviceInformation);
            counterCtrl0.setSelectedDevice(deviceInformation);
            counterCtrl0.setChannel(0);
            counterCtrl0.setEnabled(true);

            counterCtrl1.setSelectedDevice(deviceInformation);
            counterCtrl1.setChannel(1);
            counterCtrl1.setEnabled(true);

            portAmount = instantDiCtrl.getPortCount();
        } catch (Exception ex) {
            logger.error("Unable to select device!");
            ex.printStackTrace();
        }
    }

    public void setPortDirection(int p, DioPortDir d) {
        try {
            instantDoCtrl.getPortDirection()[p].setDirection(d);
        } catch (Exception e) {
            logger.warning(String.format(
                    "Unable to set direction '%s' on device '%s' !", d, p));
            e.printStackTrace();
        }
    }

    public Integer getPortAmount() {
        return portAmount;
    }

    public byte[] readData() {
        byte[] data = new byte[portAmount];
        if (hasError(instantDiCtrl.Read(0, portAmount, data)))
            logger.warning("Some error occurred during reading data");
        return data;
    }

    public void writeData(byte d, Integer p) {
        if (hasError(instantDoCtrl.Write(p, d)))
            logger.warning("Some error occurred during writing data");
    }

    public void writeData(byte[] d, Integer start) {
        if (hasError(instantDoCtrl.Write(start, portAmount, d)))
            logger.warning("Some error occurred during writing data");
    }

    public void disconnect() {
        instantDiCtrl.Cleanup();
        instantDoCtrl.Cleanup();
        logger.info("Cleaned, and disconnected");
    }

    public void printDirections() {
        logger.info("Directions:");
        for (int i = 0; i < portAmount; i++)
            logger.info(String.format("[%s] %s", i,
                    instantDiCtrl.getPortDirection()[i].getDirection()));
    }

    public void printDevices() {
        if (installedDevices.isEmpty()) {
            logger.error("No devices found!");
            return;
        }
        logger.info("Installed devices:");
        for (DeviceTreeNode d : installedDevices)
            logger.info("[" + d.DeviceNumber + "] " + d);
    }

    public void sendOneOnSpecificPos(Integer port, Integer bit) {
        int value = 0 | (1 << bit);
        System.out.println("value " + value);
        writeData((byte) value, port);
    }

    public void setZero(Integer port) {
        writeData((byte) 0, port);
    }

    public void enableTimerPulseCtrl() {
        try {
            timerPulseCtrl.setChannel(1);
            timerPulseCtrl.setFrequency(1000);
            timerPulseCtrl.setEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getCounterValue(int channel) {
        if (channel == 0) {
            return counterCtrl0.getValue();
        } else if (channel == 1) {
            return counterCtrl1.getValue();
        }
        return 0;
    }
}
