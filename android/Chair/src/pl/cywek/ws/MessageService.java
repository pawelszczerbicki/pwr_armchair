package pl.cywek.ws;

import pl.cywek.chair.Chair;
import pl.cywek.chair.ChairActivity;
import android.util.Log;

public class MessageService {
	private ChairActivity mChairActivity;
	private boolean isCalibrating = false;
	
	public MessageService(ChairActivity chairActivity) {
		mChairActivity = chairActivity;
	}
	
    public void service(Message m) {
        MessageType type = m.getType();
    	Log.d("chair", "MessageService Message: " + m.getType() + "  " + m.getCode() + " " + m.getData());

        if (type == MessageType.RESPONSE) {
        	if (m.getData().equals("START_MOVING") && !isCalibrating) {
        		mChairActivity.dialogStart("Moving", "Chair is moving now ....please wait....");
        		// block ui
        	} else if (m.getData().equals("STOP_MOVING") && !isCalibrating) {
        		//unblock ui
        		mChairActivity.dialogStop();
        	} else {
        		Chair.getInstance().setElement(m.getCode(), m.getData());
            	mChairActivity.updateValues();
        	}
        } else if (type == MessageType.CALIBRATE) {
        	Log.d("chair", "MessageService Message: c a l i b r a t e");
            // start - block UI
        	if (m.getData().equals("START")) {
        		Log.d("chair", "MessageService Message: c a l i b r a t e start");
        		isCalibrating = true;
        		mChairActivity.dialogStart("Calibrating", "Chair is calibrating now ....please wait....");
        	} else if(m.getData().equals("STOP")) {
        		Log.d("chair", "MessageService Message: c a l i b r a t e end");
        		isCalibrating = false;
        		mChairActivity.dialogStop();
        	}
        	//controller.setConfiguration(parser.getElements(m.getData()));
        } else if (type == MessageType.CONFIG) {
            //controller.setConfiguration(parser.getElements(m.getData()));
        }
    }
}
