package pl.wroc.pwr.armchair;

import Automation.BDaq.DioPortDir;
import pl.wroc.pwr.armchair.driver.Driver;

public class Main {
    private static final Integer SELECTED_DEVICE = 0;

    public static void main(String[] args) {
        Driver deviceService = Driver.getInstance();
        deviceService.selectDevice(SELECTED_DEVICE);
        deviceService.printDevices();
        deviceService.printDirections();
        deviceService.setPortDirection(3, DioPortDir.Output);
    }
}
