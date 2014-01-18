package pl.wroc.pwr.armchair;

import Automation.BDaq.DioPortDir;
import pl.wroc.pwr.armchair.driver.Driver;
import pl.wroc.pwr.armchair.logger.Logger;

public class Main {
    private static final Integer SELECTED_DEVICE = 0;
    private static Logger logger = Logger.getInstance(Main.class);
    public static void main(String[] args) throws InterruptedException {
      //TODO receive configuration from server

       logger.info("Starting App!");
        Driver deviceService = Driver.getInstance();
        deviceService.selectDevice(SELECTED_DEVICE);
        deviceService.printDevices();
        deviceService.printDirections();
        for(int i=0; i<6; i++){
            deviceService.setPortDirection(i, DioPortDir.Output);
        }
        byte b =0;
        int i=0;
//        System.out.println(getAsByte(b));
        int x = b | (1 << 2);
//		System.out.println(x);
//        System.out.println(b | (1 << 3));
//        System.out.println(b | (1 << 4));
//        System.out.println(b | (1 << 5));
//        System.out.println(b | (1 << 6));
//        System.out.println(b | (1 << 7));
        
        deviceService.sendAsByte(0, 0);
        
//        byte[] read = deviceService.readData();
//        System.out.println("" + read);
//        while(true){
//            i++;
//            System.out.println("sending: " +((byte)(i%7)));
//            deviceService.writeData(((byte) (i % 7)), 0);
//            Thread.sleep(4000);
//        }
//        deviceService.writeData(b, 1);
//        deviceService.writeData(b, 2);
//        deviceService.writeData(b, 3);
//        deviceService.writeData(b, 4);
//        deviceService.writeData(b, 5);
       // deviceService.printDirections();
        
    }
	
}
