package pl.wroc.pwr.webservice;

import pl.wroc.pwr.message.Message;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created with IntelliJ IDEA.
 * User: Pawel
 * Date: 24.10.13
 * Time: 22:39
 * To change this template use File | Settings | File Templates.
 */
@Path("/hello")
public class MockWebservice {

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Message sample(){
        Message message = new Message();
        message.setId("Message 1");
         message.setContent("Rest say hello");
        return message;
    }
}
