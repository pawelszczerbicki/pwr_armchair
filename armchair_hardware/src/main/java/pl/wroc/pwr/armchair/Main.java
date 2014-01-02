package pl.wroc.pwr.armchair;

import Automation.BDaq.DioPortDir;
import pl.wroc.pwr.armchair.driver.Driver;
import pl.wroc.pwr.armchair.logger.Logger;

public class Main {
    private static final Integer SELECTED_DEVICE = 0;
    private static Logger logger = Logger.getInstance(Main.class);
    public static void main(String[] args) {
        logger.info("Starting App!");
        Driver deviceService = Driver.getInstance();
        deviceService.selectDevice(SELECTED_DEVICE);
        deviceService.printDevices();
        deviceService.printDirections();
        deviceService.setPortDirection(3, DioPortDir.Output);
    }
}
