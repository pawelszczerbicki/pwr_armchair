package pl.cywek.chair;

import pl.cywek.ws.AtmosphereService;
import android.os.AsyncTask;


public class AtmosphereInitTask extends AsyncTask<String, Void, String> {

	private ChairActivity mChairActivity;
	
	public AtmosphereInitTask(ChairActivity chairActivity) {
		mChairActivity = chairActivity;
	}
	
	@Override
	protected String doInBackground(String... params) {
		
		new AtmosphereService(mChairActivity);
		
		return null;
	}

	@Override
	protected void onPostExecute(String response) {
	}
}
