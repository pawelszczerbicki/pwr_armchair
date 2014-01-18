package pl.wroc.pwr.armchair.mock;

import pl.wroc.pwr.armchair.driver.Driver;


public class Mock {
	private Driver driver = Driver.getInstance();

	public void start() {
		driver.enableTimerPulseCtrl();
        driver.enableFreqMeterCtrl();
	     Thread t =  new Thread() {
	    	 	int i = 0;
	        	public void run() {
	        		while (true) {
	        			i++;	        			
	        			System.out.println("" + driver.getFreqMeterCtrlValue());
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
