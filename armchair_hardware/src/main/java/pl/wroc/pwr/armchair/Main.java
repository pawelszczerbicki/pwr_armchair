package pl.wroc.pwr.armchair;

import org.apache.log4j.Logger;
import pl.wroc.pwr.armchair.ws.AtmosphereSender;
import pl.wroc.pwr.armchair.ws.AtmosphereService;

import java.io.IOException;
import java.util.Timer;

public class Main {
    private static String endpoint = "http://localhost:8080/rest/message/device";
    private static boolean mock = false;
    private static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException, IOException {
        if (args.length == 2) {
            endpoint = args[0];
            mock = Boolean.parseBoolean(args[1]);
        }

        logger.info(String.format("Trying to start for endpoint: [%s], and mock status: [%s]", endpoint, mock));
        AtmosphereSender sender = new AtmosphereService(endpoint, mock).startListening();
        Timer timer = new Timer();
        timer.schedule(new Heartbeat(sender), 10000, 60000);
    }

}
