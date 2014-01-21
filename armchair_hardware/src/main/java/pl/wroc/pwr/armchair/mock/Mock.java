package pl.wroc.pwr.armchair.mock;

import pl.wroc.pwr.armchair.driver.Driver;


public class Mock {
	private Driver driver = Driver.getInstance();

	public void start() {
		driver.enableTimerPulseCtrl();
//        driver.enableCounter();
	     Thread t =  new Thread() {
	    	 	int i = 0;
	        	public void run() {
	        		double old = 0d;
	        		while (true) {
	        			i++;	        			
	        			double freqMeterCtrlValue = driver.getCounterValue(0);
	        		
						System.out.println("" + freqMeterCtrlValue);
	        			if (freqMeterCtrlValue > old) {
	        				System.out.println("true");
	        				
	        			} else {
	        				System.out.println("false");
	        			}
	        			
	        			old = freqMeterCtrlValue;
	        			
	        			try {
							Thread.sleep(100);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	        			if (i == 1000) {
	        				break;
	        			}
	        		}
	        		
	        	};
	        };
	        
	     t.start();
	}
	
}
