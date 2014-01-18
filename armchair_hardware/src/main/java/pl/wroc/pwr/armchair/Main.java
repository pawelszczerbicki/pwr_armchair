package pl.wroc.pwr.armchair;

import pl.wroc.pwr.armchair.ws.AtmosphereService;

public class Main {
    private static final Integer SELECTED_DEVICE = 0;
    public static void main(String[] args) throws InterruptedException {
        new AtmosphereService();
//        atmosphereService.send(new Message(MessageType.RESPONSE, "aaaaaa"));
//        logger.error("error, sending:)");
      //TODO receive configuration from server
//        Message m= new Message();
//
//        m.setType(MessageType.CONFIG);
//        m.setData("data11111111111");
//        atmosphereService.send(m);
//        Thread.sleep(10000);
//       logger.info("Starting App!");
//        Driver deviceService = Driver.getInstance();
//        deviceService.selectDevice(SELECTED_DEVICE);
//        deviceService.printDevices();
//        deviceService.printDirections();
//        for(int i=0; i<6; i++){
//            deviceService.setPortDirection(i, DioPortDir.Output);
//        }
//        byte b =0;
//        int i=0;
////        System.out.println(getAsByte(b));
//        int x = b | (1 << 2);
////		System.out.println(x);
////        System.out.println(b | (1 << 3));
////        System.out.println(b | (1 << 4));
////        System.out.println(b | (1 << 5));
////        System.out.println(b | (1 << 6));
////        System.out.println(b | (1 << 7));
//
//        deviceService.sendAsByte(0, 0);
//
////        byte[] read = deviceService.readData();
////        System.out.println("" + read);
////        while(true){
////            i++;
////            System.out.println("sending: " +((byte)(i%7)));
////            deviceService.writeData(((byte) (i % 7)), 0);
////            Thread.sleep(4000);
////        }
////        deviceService.writeData(b, 1);
////        deviceService.writeData(b, 2);
////        deviceService.writeData(b, 3);
////        deviceService.writeData(b, 4);
////        deviceService.writeData(b, 5);
//       // deviceService.printDirections();
//
    }
	
}
