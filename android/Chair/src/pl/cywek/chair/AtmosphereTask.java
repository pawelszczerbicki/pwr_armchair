package pl.cywek.chair;

import pl.cywek.ws.AtmosphereSender;
import pl.cywek.ws.Message;
import pl.cywek.ws.MessageType;
import android.os.AsyncTask;
import android.util.Log;

public class AtmosphereTask extends AsyncTask<String, Void, String> {

	@Override
	protected String doInBackground(String... params) {

		if (params[0] == null || params[1] == null || params[2] == null) {
			return null;
		}
		Message m = new Message();

		m.setData(params[0]);
		m.setType(MessageType.valueOf(params[1]));
		m.setCode(params[2]);

		Log.d("chair", "AtmosphereTask: sending message " + m.getType() + " "
				+ m.getCode() + " " + m.getData());

		AtmosphereSender sender = AtmosphereSender.getInstance();
		sender.send(m);

		return "AtmosphereTask: sending message complete " + m.getType() + " "
				+ m.getCode() + " " + m.getData();
	}

	@Override
	protected void onPostExecute(String response) {

		Log.d("chair", response);
	}
}
