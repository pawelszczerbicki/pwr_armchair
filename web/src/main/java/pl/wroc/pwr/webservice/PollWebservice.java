package pl.wroc.pwr.webservice;

import org.apache.log4j.Logger;
import org.atmosphere.annotation.Suspend;
import org.atmosphere.config.service.AtmosphereService;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.jersey.JerseyBroadcaster;
import pl.wroc.pwr.message.Message;
import pl.wroc.pwr.message.MessageType;

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
        Broadcaster b = BroadcasterFactory.getDefault().lookup(device);
        b.broadcast(m);
        if(device.equals("cywek")){
            runMock(b, m);
        }
    }

    private void runMock(Broadcaster b, Message m) {
        if(m.getType() == MessageType.ACTION){
            Message responseMessage = new Message();
            responseMessage.setType(MessageType.RESPONSE);
            responseMessage.setData("MOVING");
            b.broadcast(m);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            responseMessage.setCode("PF");
            responseMessage.setData(String.valueOf(new Double(Math.random() * 100).intValue()));
            b.broadcast(responseMessage);
        } else if(m.getType() == MessageType.CALIBRATE){
             Message calibrating = new Message();
            calibrating.setType(MessageType.CALIBRATE);
            calibrating.setData("START");
            b.broadcast(calibrating);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(int i=0; i<9; i++){
                calibrating.setType(MessageType.RESPONSE);
                calibrating.setCode("PF");
                calibrating.setData(String.valueOf(new Double(Math.random() * 100).intValue()));
                b.broadcast(calibrating);
            }

            calibrating.setType(MessageType.CALIBRATE);
            calibrating.setData("STOP");
            b.broadcast(calibrating);
        }

    }
}
