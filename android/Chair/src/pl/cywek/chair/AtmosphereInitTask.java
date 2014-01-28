package pl.cywek.chair;

import pl.cywek.ws.AtmosphereSender;
import pl.cywek.ws.AtmosphereService;
import pl.cywek.ws.Message;
import pl.cywek.ws.MessageType;
import android.os.AsyncTask;
import android.util.Log;


public class AtmosphereInitTask extends AsyncTask<String, Void, String> {

	private ChairActivity mChairActivity;
	
	public AtmosphereInitTask(ChairActivity chairActivity) {
		mChairActivity = chairActivity;
	}
	
	@Override
	protected String doInBackground(String... params) {
	
//Message m = new Message();
		
//		m.setData("30");
//		m.setType(MessageType.ACTION);
//		m.setCode("PF");
		
		
		new AtmosphereService(mChairActivity);
//		AtmosphereSender sender = AtmosphereSender.getInstance();
//		sender.send(m);
		
		return null;
	}

	@Override
	protected void onPostExecute(String response) {
	}
}
