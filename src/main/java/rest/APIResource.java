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
        return "{\"msg\":\"Hello World from APIResource\"}";
    }

    //    ca1/{number}
    //    Get information about a person (address, hobbies etc) given a phone number
    @GET
    @Path("{number}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonByNumber() {
        return "{\"msg\":\"Here you get a person with a given phone number\"}";
    }


    //    ca1/{hobbyname}
    //    Get all persons with a given hobby
//    @GET
//    @Path("{hobbyname}")
//    @Produces({MediaType.APPLICATION_JSON})
//    public String getPersonByHobby() {
//        return "{\"msg\":\"Here you get a list of all persons given a hobby\"}";
//    }

//    ca1/{zipCode}
//    Get all persons living in a given city (i.e. 2800 Lyngby)

//    ca1/count/{hobbyname}
//    Get the number of people with a given hobby

//    ca1/count/zipcodes
//    Get a list of all zip codes in Denmark

//    ca1/
//    Create new Persons

//    ca1/{id}
//    Edit Persons




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
