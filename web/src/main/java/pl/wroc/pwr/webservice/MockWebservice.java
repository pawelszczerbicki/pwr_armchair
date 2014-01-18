package pl.wroc.pwr.webservice;

import org.apache.commons.lang3.RandomStringUtils;
import pl.wroc.pwr.motor.Motor;
import pl.wroc.pwr.motor.MotorDto;
import pl.wroc.pwr.motor.MotorSettings;
import pl.wroc.pwr.utils.FailResponse;
import pl.wroc.pwr.utils.JsonResponse;
import pl.wroc.pwr.utils.SuccessResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Pawel
 * Date: 24.10.13
 * Time: 22:39
 * To change this template use File | Settings | File Templates.
 */
@Path("/motors")
public class MockWebservice {

    private static Map<String, Motor> motors = new HashMap<String, Motor>();

    static {
        motors.put("1", getRandomMotor("1"));
        motors.put("2", getRandomMotor("2"));
        motors.put("3", getRandomMotor("3"));
        motors.put("4", getRandomMotor("4"));
        motors.put("5", getRandomMotor("5"));
        motors.put("6", getRandomMotor("6"));
        motors.put("7", getRandomMotor("7"));
        motors.put("8", getRandomMotor("8"));
    }

    @Path("/move")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JsonResponse move(List<MotorDto> motors) {
        if (motors.size() >= 1 && motors.get(0).getValue() == 0)
            return FailResponse.create("Value can not be applied");
        return SuccessResponse.create();
    }

    @Path("/get-states")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse<List<MotorDto>> getState() {
        return SuccessResponse.create(toMotorDto(motors.values()));
    }

    @Path("{motorId}/get-state")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JsonResponse getState(@PathParam("motorId") String id) {
        if (!motors.containsKey(id))
            return FailResponse.create("No such motor!");
        Motor m = motors.get(id);
        return SuccessResponse.create(new MotorDto(m.getId(), m.getValue()));
    }

    @Path("{motorId}/get-settings")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse getSettings(@PathParam("motorId") String id) {
        if (!motors.containsKey(id))
            return FailResponse.create("No such motor!");
        return SuccessResponse.create(motors.get(id).getSettings());
    }

    private static Motor getRandomMotor(String id) {
        Random r = new Random(100);
        return new Motor(id, RandomStringUtils.randomAlphanumeric(5), r.nextDouble(), new MotorSettings(r.nextInt(), r.nextInt(), r.nextInt()));
    }
    private List<MotorDto> toMotorDto(Collection<Motor> ms) {
        List<MotorDto> md = new ArrayList<MotorDto>();
        for (Motor m : ms) {
            md.add(new MotorDto(m.getId(), m.getValue()));
        }
        return md;
    }

}
