package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.FullPersonDTO;
import dtos.PersonsDTO;
import dtos.ZipcodesDTO;
import errorhandling.ExceptionDTO;
import facades.APIFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

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

    //    ca1/person/phone/{number}
    //    Get information about a person (address, hobbies etc.) given a phone number
    @GET
    @Path("person/phone/{number}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonByNumber(@PathParam("number") String number) {
        try {
            FullPersonDTO fullPersonDTO = FACADE.getPersonByPhone(number);
            return GSON.toJson(fullPersonDTO);
        } catch (Exception e) {
            ExceptionDTO exceptionDTO = new ExceptionDTO(404, String.format("No person with number %s found", number));
            return GSON.toJson(exceptionDTO);
        }
    }


    //    ca1/person/hobby/{hobbyname}
    //    Get all persons with a given hobby
    @GET
    @Path("person/hobby/{hobbyname}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonsByHobby(@PathParam("hobbyname") String hobbyname) {
        try {
            PersonsDTO personsDTO = new PersonsDTO(FACADE.getPersonsByHobby(hobbyname));
            return GSON.toJson(personsDTO);
        } catch (Exception e) {
            ExceptionDTO exceptionDTO = new ExceptionDTO(404, String.format("No hobby with name %s found", hobbyname));
            return GSON.toJson(exceptionDTO);
        }
    }

//    ca1/person/city/{zipCode}
//    Get all persons living in a given city (i.e. 2800 Lyngby)
    @GET
    @Path("/person/city/{zipCode}")
    @Produces("application/json")
    public String getAllFromCity(@PathParam("zipCode") String zipCode) {
        try {
            PersonsDTO personsDTO = new PersonsDTO(FACADE.getAllFromCity(zipCode));
            return GSON.toJson(personsDTO);
        } catch (Exception e) {
            ExceptionDTO exceptionDTO = new ExceptionDTO(404, String.format("No city with zipcode %s found", zipCode));
            return GSON.toJson(exceptionDTO);
        }
    }

//    ca1/hobby/{hobbyname}/count
//    Get the number of people with a given hobby
    @GET
    @Path("hobby/{hobbyname}/count")
    @Produces("application/json")
    public String getNumberWithHobby(@PathParam("hobbyname") String hobbyname) {
        try {
            int count = FACADE.getNumberWithHobby(hobbyname);
            return String.format("{\"count\":\"%d\"}", count);
        } catch (Exception e) {
            ExceptionDTO exceptionDTO = new ExceptionDTO(404, String.format("No hobby with name %s found", hobbyname));
            return GSON.toJson(exceptionDTO);
        }
    }

//    ca1/zipcodes
//    Get a list of all zip codes in Denmark
// should the path be changed? It seems weirdly named, when the return value is a list, not a number
//    Thorbjørn: Jeg tænker at denne ville følge Jons logik: ca1/zipCode
//    ved kald af typen getAll ser det også ud til at han bruger ental og hvis der ikke står noget efter / er all underforstået
    @GET
    @Path("zipcodes")
    @Produces("application/json")
    public String getAllZipcodes() {
        ZipcodesDTO zipCodes = FACADE.getAllZipcodes();
        return GSON.toJson(zipCodes);
    }

//    ca1/person
//    Create new Persons
    @POST
    @Path("person")
    @Consumes("application/json")
    @Produces("application/json")
    public String createPerson(String person) { // input is the body of the request, generated in the frontend
        return "{\"msg\":\"Input is correct, return a person with added id\"}";
    }

//    ca1/person/{id}
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
