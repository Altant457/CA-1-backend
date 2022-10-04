package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.*;
import entities.Person;
import facades.APIFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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
            String output = GSON.toJson(fullPersonDTO);
            System.out.println(output);
            return output;
        } catch (Exception e) {
            throw new WebApplicationException(String.format("No person with phone number \"%s\" found", number), 404);
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
            throw new WebApplicationException(String.format("No hobby with name \"%s\" found", hobbyname), 404);
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
            throw new WebApplicationException(String.format("No city with zipcode \"%s\" found", zipCode), 404);
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
            throw new WebApplicationException(String.format("No hobby with name \"%s\" found", hobbyname), 404);
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

    @GET
    @Path("hobby")
    @Produces("application/json")
    public String getAllHobbies() {
        List<HobbyDTO> hobbies = FACADE.getAllHobbies();
        return GSON.toJson(hobbies);
    }

//    ca1/person
//    Create new Persons
    @POST
    @Path("person")
    @Consumes("application/json")
    @Produces("application/json")

    public String createPerson(String personJSON) { // input is the body of the request, generated in the frontend
//        System.out.println("vi nåede til create person");
//        System.out.println(personJSON);
//        Person newPerson = GSON.fromJson(personJSON, Person.class);
        FullPersonDTO newFullPersonDTO = GSON.fromJson(personJSON, FullPersonDTO.class);
//        System.out.println(newFullPersonDTO);
        if (!Objects.equals(newFullPersonDTO.getEmail(), null)
                && !Objects.equals(newFullPersonDTO.getFirstName(), null)
                && !Objects.equals(newFullPersonDTO.getLastName(), null)) {
            Person newPerson = new Person(newFullPersonDTO);
//            System.out.println("The person from FullPersonDTO");
//            System.out.println(newPerson);
            Person createdPerson = FACADE.createPerson(newPerson);
            FullPersonDTO fullPersonDTO = new FullPersonDTO(createdPerson);
            return GSON.toJson(fullPersonDTO);
        } else {
            List<String> msg = new ArrayList<>();
            if (Objects.equals(newFullPersonDTO.getFirstName(), null)) msg.add("Field \"First name\" is required. ");
            if (Objects.equals(newFullPersonDTO.getLastName(), null)) msg.add("Field \"Last name\" is required. ");
            if (Objects.equals(newFullPersonDTO.getEmail(), null)) msg.add("Field \"Email\" is required. ");
            throw new WebApplicationException(String.join("\n", msg), 400);
        }

    }

//    ca1/person/{id}
//    Edit Persons
    @PUT
    @Path("person/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public String editPerson(@PathParam("id") String id, String personJSON) {
//        Person person = GSON.fromJson(personJSON, Person.class);
        FullPersonDTO fullPersonDTO = GSON.fromJson(personJSON, FullPersonDTO.class);
        if (!Objects.equals(fullPersonDTO.getEmail(), "")
                && !Objects.equals(fullPersonDTO.getFirstName(), "")
                && !Objects.equals(fullPersonDTO.getLastName(), "")) {
//            System.out.println("lige før facadekald til edit er vores personobjekt: " +fullPersonDTO);
            Person putPerson = new Person(fullPersonDTO);
            FullPersonDTO editedPersonDTO = FACADE.editPerson(fullPersonDTO);
//            Person editedPerson = FACADE.editPerson(putPerson);
//            PersonDTO personDTO = new PersonDTO(editedPerson);
//            return GSON.toJson(personDTO);
//            System.out.println("den returnerede person efter facadekald er: " + editedPersonDTO);
            return GSON.toJson(editedPersonDTO);
        } else {
            List<String> msg = new ArrayList<>();
            if (Objects.equals(fullPersonDTO.getFirstName(), "")) msg.add("Field \"First name\" is required. ");
            if (Objects.equals(fullPersonDTO.getLastName(), "")) msg.add("Field \"Last name\" is required. ");
            if (Objects.equals(fullPersonDTO.getEmail(), "")) msg.add("Field \"Email\" is required. ");
            throw new WebApplicationException(String.join("\n", msg), 400);
        }
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

    @DELETE
    @Path("person/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") Long id) throws EntityNotFoundException {
        PersonDTO deleted = new PersonDTO(FACADE.delete(id));
        return Response.ok().entity(GSON.toJson(deleted)).build();
    }














}
