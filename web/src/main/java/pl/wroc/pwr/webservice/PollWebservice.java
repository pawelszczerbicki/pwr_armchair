package pl.wroc.pwr.webservice;

import org.apache.log4j.Logger;
import org.atmosphere.annotation.Suspend;
import org.atmosphere.config.service.AtmosphereService;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.jersey.JerseyBroadcaster;
import pl.wroc.pwr.message.Message;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.TimeUnit;

import static org.apache.log4j.Logger.getLogger;

/**
 * Created by Pawel on 18.01.14.
 */
@Path("/message")
@AtmosphereService(broadcaster = JerseyBroadcaster.class)
public class PollWebservice {

    private final Logger logger = getLogger(getClass());

    @Suspend
    @GET
    @Path("{device}")
    @Produces(MediaType.APPLICATION_JSON)
    public String suspend(@Context AtmosphereResource atmosphereResource, @PathParam("device") String device) {
        Broadcaster privateChannel = BroadcasterFactory.getDefault().lookup(device, true);
        atmosphereResource.suspend(12, TimeUnit.MINUTES);
        privateChannel.addAtmosphereResource(atmosphereResource);
        logger.info("Socket opened for: " + device);
        return "";
    }

    @POST
    @Path("{device}")
    @Produces(MediaType.APPLICATION_JSON)
    public void broadcast(Message m, @PathParam("device") String device) throws InterruptedException {
        logger.info(String.format("Send message by: %s, content: %s ", device, m));
        BroadcasterFactory.getDefault().lookup(device).broadcast(m);
    }
}
