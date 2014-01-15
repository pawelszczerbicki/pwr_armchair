package pl.wroc.pwr.armchair.ws;

import com.google.gson.Gson;
import org.atmosphere.wasync.*;
import pl.wroc.pwr.armchair.logger.Logger;

import java.io.IOException;

/**
 * Created by Pawel on 14.01.14.
 */
public class AtmosphereService {

    static Logger logger = Logger.getInstance(AtmosphereService.class);

    //TODO move to properties
    //TODO could loop logger because of bad websocket, Create additional method without sending log
    private static String REMOTE_ADDRESS = "http://pawelszczerbicki.pl:8080/armchair/driver";

    private Socket socket;

    private MessageService messageService;

    public AtmosphereService(MessageService ms) {
        messageService = ms;
        final Gson gson = new Gson();
        Client client = ClientFactory.getDefault().newClient();
        RequestBuilder request = getTransport(gson, client);
        socket = client.create();
        logger.info("Socket created with state: " + socket.status());
        try {
            overrideMethodsAndOpenSocket(request);
        } catch (IOException e) {
            logger.error("Could not initialize socket.");
        }
    }

    public void send(Message m) {
        if (socket == null) throw new IllegalStateException("Socket is not initialized!");
        try {
            socket.fire(m);
        } catch (IOException e) {
            logger.error("Could not fire socket and send message");
        }
    }

    private void overrideMethodsAndOpenSocket(RequestBuilder request) throws IOException {
        socket.on(new Function<Message>() {
            @Override
            public void on(Message m) {
                logger.info("message: " + m.getType());                   ;
                messageService.service(m);
            }
        }).on(new Function<IOException>() {

            @Override
            public void on(IOException e) {
                logger.error("Socket exception ");
            }
        }).open(request.build());
    }

    private RequestBuilder getTransport(final Gson gson, Client client) {
        return client.newRequestBuilder()
                .method(Request.METHOD.GET)
                .uri(REMOTE_ADDRESS)
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
                        return gson.fromJson(s.split("\\|")[1], Message.class);
                    }
                })
                .transport(Request.TRANSPORT.WEBSOCKET)
                .transport(Request.TRANSPORT.LONG_POLLING);
    }
}
