package pl.wroc.pwr.armchair.ws;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.atmosphere.wasync.*;
import pl.wroc.pwr.armchair.armchair.ArmchairController;

import java.io.IOException;

import static java.lang.String.format;

/**
 * Created with IntelliJ IDEA.
 * User: Pawel
 * Date: 10.10.13
 * Time: 22:15
 * To change this template use File | Settings | File Templates.
 */
public class AtmosphereService {
    private static Logger logger = Logger.getLogger(AtmosphereService.class);
    private String endpoint;
    private boolean mocked;
    private MessageService messageService;
    private Socket socket;
    private RequestBuilder request;

    public AtmosphereService(String endpoint, Boolean mocked) {
        this.mocked = mocked;
        this.endpoint = endpoint;
        final Gson gson = new Gson();
        Client client = ClientFactory.getDefault().newClient();
        request = getTransport(gson, client);
        socket = client.create();
    }

    public AtmosphereSender startListening() throws IOException {
        socket.on(new Function<Message>() {
            @Override
            public void on(Message m) {
                messageService.service(m);
            }
        }).on(new Function<IOException>() {

            @Override
            public void on(IOException e) {
                logger.error("Socket exception ", e);
            }
        }).open(request.build());
        AtmosphereSender sender = new AtmosphereSender(socket);
        createObjects(sender);
        logger.info(format("Socket created with state: [%s] for address: [%s]", socket.status(), endpoint));
        return sender;
    }

    private RequestBuilder getTransport(final Gson gson, Client client) {
        return client.newRequestBuilder()
                .method(Request.METHOD.GET)
                .uri(endpoint)
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

    private void createObjects(AtmosphereSender sender) {
       this.messageService = new MessageService(new ArmchairController(sender, mocked));
    }
}
