package rest;

import dtos.FullPersonDTO;
import dtos.PersonDTO;
import entities.*;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

class APIResourceTest {
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    private static Person p1, p2, p3;
    private static PersonDTO p1DTO, p2DTO, p3DTO;
    private static FullPersonDTO fp3DTO;
    private static Hobby h1, h2, h3;
    private static Address a1, a2, a3;
    private static Phone ph1, ph2, ph3;


    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        em.createQuery("delete from Phone").executeUpdate();
        em.createQuery("delete from Person").executeUpdate();
        em.createQuery("delete from Address").executeUpdate();

        p1 = new Person("Testperson1", "testemail");
        p2 = new Person("Testperson2", "testemail2");
        p3 = new Person("Testperson3", "testemail3");
        a1 = new Address("some street", "th", em.find(CityInfo.class, "3720"));
        ph1 = new Phone("12345678", "hjemmetelefon");
        h1 = em.find(Hobby.class, 1L);
        h2 = em.find(Hobby.class, 2L);
        h3 = em.find(Hobby.class, 3L);

        p3.setLastName("Lname");
        p3.setAddress(a1);
        p2.setAddress(a1);
        p3.addHobbytoHobbySet(h1);
        p3.addPhone(ph1);

        p1.addHobbytoHobbySet(h1);
        p2.addHobbytoHobbySet(h2);

        em.persist(a1);
        em.persist(ph1);
        em.persist(p1);
        em.persist(p2);
        em.persist(p3);

        em.getTransaction().commit();
        em.close();

        p1DTO = new PersonDTO(p1);
        p2DTO = new PersonDTO(p2);
        p3DTO = new PersonDTO(p3);
        fp3DTO = new FullPersonDTO(p3);

    }


    @Test
    void demo() {
        given()
                .when()
                .get("/ca1/")
                .then()
                .statusCode(200);
    }


    @Test
    void getPersonByNumber() {
        given()
                .contentType("application/json")
//                .contentType(ContentType.JSON)
                .pathParam("number", ph1.getNumber())
                .when()
                .get("ca1/person/phone/{number}")
//                .get("ca1/person/phone/12345678")
                .then()
                .statusCode(200)
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("firstName",equalTo(p3DTO.getFirstName()));
////                .body("children", hasItems(hasEntry("name","Joseph"),hasEntry("name","Alberta")));

    }


    @Test
    void getPersonsByHobby() {
    }

    @Test
    void getAllFromCity() {
    }

    @Test
    public void getNumberWithHobby() {
        given()
                .contentType("application/json")
                .pathParam("hobby", h1.getName())
                .when()
                .get("ca1/hobby/{hobby}/count")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("count", equalTo("2"));
    }

    @Test
    void getAllZipcodes() {
    }

    @Test
    void createPerson() {
    }

    @Test
    void editPerson() {
    }
}