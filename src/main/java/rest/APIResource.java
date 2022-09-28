package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.FacadeExample;

import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

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
    //    Get information about a person (address, hobbies etc.) given a phone number
    @GET
    @Path("{number}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonByNumber(@PathParam("number") String number) {
        return String.format("{\"msg\":\"Here you get a person with a given phone number. The number was %s\"}", number);
    }


    //    ca1/{hobbyname}
    //    Get all persons with a given hobby
    @GET
    @Path("{hobbyname}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonByHobby(@PathParam("hobbyname") String hobbyname) {
        return String.format("{\"msg\":\"Here you get a list of all persons given a hobby. The hobby given was %s\"}", hobbyname);
    }

//    ca1/{zipCode}
//    Get all persons living in a given city (i.e. 2800 Lyngby)
    @GET
    @Path("{zipCode}")
    @Produces("application/json")
    public String getAllFromCity(@PathParam("zipCode") String zipCode) {
        return String.format("{\"msg\":\"Here you get a list of all persons (in the db) who lives in a specific city. The given city was %s\"}", zipCode);
    }

//    ca1/count/{hobbyname}
//    Get the number of people with a given hobby
    @GET
    @Path("count/{hobbyname}")
    @Produces("application/json")
    public String getAllWithHobby(@PathParam("hobbyname") String hobbyname) {
        return String.format("{\"msg\":\"Here you get the number of persons with the given hobby. The given hobby was 5%s\"}", hobbyname);
    }

//    ca1/count/zipcodes     // should the path be changed? It seems weirdly named, when the return value is a list, not a number
//    Get a list of all zip codes in Denmark
    @GET
    @Path("count/zipcodes")
    @Produces("application/json")
    public String getAllZipcodes() {
        return "{\"msg\":\"Here you get a list of all the zipcodes in Denmark";
    }

//    ca1/
//    Create new Persons
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public String createPerson(String input) { // input is the body of the request, generated in the frontend
        return "{\"msg\":\"Input is correct, return a person with added id\"";
    }

//    ca1/{id}
//    Edit Persons
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public String editPerson(@PathParam("id") String id, String input) {
        return String.format("{\"msg\":\"Provided a person with id = %s exists, change the person to equal the input\"", id);
    }


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
