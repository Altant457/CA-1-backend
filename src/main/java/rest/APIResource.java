package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.RenameMeDTO;
import facades.FacadeExample;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("ca1")
public class APIResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final FacadeExample FACADE = FacadeExample.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();




    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @GET
    @Path("person")
    @Produces("application/json")
    public String test(String input) {

        return "";
    }

//    ca1/hobbies

//    ca1/{hobbyname}

//    ca1/{zipCode}

//    ca1/cityinfo/all

//    ca1/address/{id}

//    ca1/    create new person

//    ca1/{id} edit person









//    @Path("count")
//    @GET
//    @Produces({MediaType.APPLICATION_JSON})
//    public String getRenameMeCount() {
//
//        long count = FACADE.getRenameMeCount();
//        //System.out.println("--------------->"+count);
//        return "{\"count\":" + count + "}";  //Done manually so no need for a DTO
//    }
//
//    @POST
//    @Produces({MediaType.APPLICATION_JSON})
//    @Consumes({MediaType.APPLICATION_JSON})
//    public Response postExample(String input) {
//        RenameMeDTO rmdto = GSON.fromJson(input, RenameMeDTO.class);
//        System.out.println(rmdto);
//        return Response.ok().entity(rmdto).build();
//    }
}
