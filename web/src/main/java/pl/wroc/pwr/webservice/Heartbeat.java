package pl.wroc.pwr.webservice;

import org.apache.log4j.Logger;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.wroc.pwr.message.Message;

import static org.apache.log4j.Logger.getLogger;
import static pl.wroc.pwr.message.MessageType.HEARTBEAT;

/**
 * Created by Pawel on 18.01.14.
 */
@Component
public class Heartbeat {
    private final Logger logger  = getLogger(getClass());

    @Scheduled(cron = "0 0/5 * * * *")
    public void heartBeat(){
        Broadcaster b = BroadcasterFactory.getDefault().lookup("/*");
        logger.info(b.getID());
        b.broadcast(new Message(HEARTBEAT));
    }

}
