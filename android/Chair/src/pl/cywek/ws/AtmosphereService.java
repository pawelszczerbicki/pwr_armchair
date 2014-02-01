package pl.cywek.ws;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import org.atmosphere.wasync.*;

import pl.cywek.chair.ChairActivity;

import java.io.IOException;

public class AtmosphereService {
    private String LOCAL_ADDRESS = "http://pawelszczerbicki.pl:8080/armchair/rest/message/device";
    private MessageService messageService;
    private static Socket socket;
    private RequestBuilder request;
    private ChairActivity mChairActivity;

    public AtmosphereService(ChairActivity chairActivity) {
    	mChairActivity = chairActivity;
        final Gson gson = new Gson();
        Client client = ClientFactory.getDefault().newClient();
        request = getTransport(gson, client);
        socket = client.create();
        Log.d("chair", "Socket created with state: " + socket.status());
        messageService = new MessageService(mChairActivity);
        try {
            overrideMethodsAndOpenSocket(request);
        } catch (IOException e) {
        	Log.d("chair", "Could not initialize socket.", e);
        }
        AtmosphereSender.getInstance().setSocket(socket);
    }

    private void overrideMethodsAndOpenSocket(RequestBuilder request) throws IOException {
        socket.on(new Function<Message>() {
            @Override
            public void on(Message m) {
                messageService.service(m);
            }
        }).on(new Function<IOException>() {

            @Override
            public void on(IOException e) {
            	Log.d("chair", "Socket exception ", e);
            }
        }).open(request.build());
    }

    private RequestBuilder getTransport(final Gson gson, Client client) {
    	SharedPreferences prefs = mChairActivity.getPreferences(mChairActivity.MODE_PRIVATE); 
    	String restoredText = prefs.getString("host", "localhost");
    	return client.newRequestBuilder()
                .method(Request.METHOD.GET)
                .uri(restoredText) //"http://pawelszczerbicki.pl:8080/armchair/rest/message/device")
                .header("Content-Type", "application/json")
                .encoder(new Encoder<Message, String>() {
                    @Override
                    public String encode(Message m) {
                        return gson.toJson(m);
                    }
                })
                .decoder(new Decoder<String, Message>() {
                    @Override
                    public Message decode(Event e, String s) {
                    	Log.d("chair", "Atmosphereervice " + s);
                    	// Fix dla Pawla super serwera
                    	if (s.equals("OPEN") || s.equals("CLOSE")) {
                    		return null;
                    	}
                        return gson.fromJson(s.split("\\|")[1], Message.class);
                    }
                })
                .transport(Request.TRANSPORT.WEBSOCKET)
                .transport(Request.TRANSPORT.LONG_POLLING);
    }
}
