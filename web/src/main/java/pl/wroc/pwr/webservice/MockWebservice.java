package pl.wroc.pwr.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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
    @Produces("application/json")
    public String sample(){
        return "Rest say hello";
    }
}
