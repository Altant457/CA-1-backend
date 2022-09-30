package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.FullPersonDTO;
import dtos.PersonDTO;
import errorhandling.ExceptionDTO;
import facades.APIFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("ca1")
public class APIResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final APIFacade FACADE = APIFacade.getInstance(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World from APIResource\"}";
    }

    //    ca1/{number}
    //    Get information about a person (address, hobbies etc.) given a phone number
    @GET
    @Path("person/phone/{number}")
    @Produces({MediaType.APPLICATION_JSON})
    //TODO String number
    public String getPersonByNumber(@PathParam("number") String number) {
        try {
            FullPersonDTO personDTO = FACADE.getPersonByPhone(number);
            return GSON.toJson(personDTO);
        } catch (Exception e) {
            ExceptionDTO exceptionDTO = new ExceptionDTO(404, String.format("No person with number %s found", number));
            return GSON.toJson(exceptionDTO);
        }
    }


    //    ca1/{hobbyname}
    //    Get all persons with a given hobby
    @GET
    @Path("person/hobby/{hobbyname}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonsByHobby(@PathParam("hobbyname") String hobbyname) {
        return String.format("{\"msg\":\"Here you get a list of all persons given a hobby. The hobby given was %s\"}", hobbyname);
    }

//    ca1/{zipCode}
//    Get all persons living in a given city (i.e. 2800 Lyngby)
    @GET
    @Path("/person/city{zipCode}")
    @Produces("application/json")
    public String getAllFromCity(@PathParam("zipCode") String zipCode) {
        return String.format("{\"msg\":\"Here you get a list of all persons (in the db) who lives in a specific city. The given zipcode was %s\"}", zipCode);
    }

//    ca1/count/{hobbyname}
//    Get the number of people with a given hobby
    @GET
    @Path("hobby/{hobbyname}/count")
    @Produces("application/json")
    public String getAllWithHobby(@PathParam("hobbyname") String hobbyname) {
        return String.format("{\"msg\":\"Here you get the number of persons with the given hobby. The given hobby was %s\"}", hobbyname);
    }

//    ca1/count/zipcodes     // should the path be changed? It seems weirdly named, when the return value is a list, not a number
//    Get a list of all zip codes in Denmark
    @GET
    @Path("zipcodes")
    @Produces("application/json")
    public String getAllZipcodes() {
        return "{\"msg\":\"Here you get a list of all the zipcodes in Denmark\"}";
    }

//    ca1/
//    Create new Persons
    @POST
    @Path("person")
    @Consumes("application/json")
    @Produces("application/json")
    public String createPerson(String input) { // input is the body of the request, generated in the frontend
        return "{\"msg\":\"Input is correct, return a person with added id\"}";
    }

//    ca1/{id}
//    Edit Persons
    @PUT
    @Path("person/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public String editPerson(@PathParam("id") String id, String input) {
        return String.format("{\"msg\":\"Provided a person with id = %s exists, change the person to equal the input\"}", id);
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
