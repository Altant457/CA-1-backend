package facades;

import dtos.FullPersonDTO;
import dtos.PersonDTO;
import dtos.ZipcodesDTO;
import entities.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertEquals;

class APIFacadeTest {
    private static EntityManagerFactory emf;
    private static APIFacade facade;
    private Person p1, p2, p3;
    private PersonDTO p1DTO, p2DTO, p3DTO;
    private FullPersonDTO fp1DTO, fp2DTO, fp3DTO;
    private Hobby h1, h2, h3;
    private Address a1, a2, a3;
    private Phone ph1, ph2, ph3;
    private CityInfo c1, c2, c3;

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = APIFacade.getInstance(emf);
    }

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        em.createQuery("delete from Phone").executeUpdate();
        em.createQuery("delete from Person").executeUpdate();
        em.createQuery("delete from Address").executeUpdate();
        em.createQuery("delete from Hobby").executeUpdate();
        em.createQuery("delete from CityInfo").executeUpdate();

        p1 = new Person("testemail1", "Test1", "person");
        p2 = new Person("testemail2", "Test2", "person");
        p3 = new Person("testemail3", "Test3", "person");
        c1 = new CityInfo("1234", "testCity");
        c2 = new CityInfo("1616", "testBy");
        c3 = new CityInfo("6842", "byTest");
        a1 = new Address("some street", "th", c1);
        ph1 = new Phone("12345678", "hjemmetelefon");
        h1 = new Hobby("a", "a", "a", "a");
        h2 = new Hobby("b", "b", "b", "b");
        h3 = new Hobby("c", "c", "c", "c");


        p3.setLastName("Lname");
        p1.setAddress(a1);
        p2.setAddress(a1);
        p3.setAddress(a1);
        p3.addHobbytoHobbySet(h1);
        p3.addPhone(ph1);

        p1.addHobbytoHobbySet(h1);
        p2.addHobbytoHobbySet(h2);
        p2.addHobbytoHobbySet(h1);
        p3.addHobbytoHobbySet(h2);
        p3.addHobbytoHobbySet(h1);

        em.persist(h1);
        em.persist(h2);
        em.persist(h3);
        em.persist(c1);
        em.persist(c2);
        em.persist(c3);
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
        fp1DTO = new FullPersonDTO(p1);
        fp2DTO = new FullPersonDTO(p2);
        fp3DTO = new FullPersonDTO(p3);
    }

    @Test
    void getPersonByPhone() {
        FullPersonDTO actual = facade.getPersonByPhone(p3.getPhone().iterator().next().getNumber());
        assertEquals(fp3DTO, actual);
    }

    @Test
    void getPersonByHobby() {
        List<FullPersonDTO> actual = facade.getPersonsByHobby(p1.getHobbies().iterator().next().getName());
        assertThat(actual, containsInAnyOrder(fp1DTO, fp2DTO, fp3DTO));


    }

    @Test
    void getAllFromCity() {
        List<PersonDTO> actual = facade.getAllFromCity(a1.getCityInfo().getZipCode());
        assertThat(actual, hasItems(p3DTO, p2DTO));
    }

    @Test
    void getNumberWithHobby() {
        int actual1 = facade.getNumberWithHobby(h1.getName());
        int actual2 = facade.getNumberWithHobby(h2.getName());

        assertEquals(3, actual1);
        assertEquals(2, actual2);
    }

    @Test
    void getAllZipcodes() {
        ZipcodesDTO actual = facade.getAllZipcodes();
        assertThat(actual.getAll(), hasItems("1234", "1616", "6842"));
    }


    @Test
    void createPerson() {
        EntityManager em = emf.createEntityManager();
        Person newPerson = new Person("testMail", "fName", "lName");
        Phone newPhone = new Phone("87654321", "testPhone");
        Address newAddress = new Address("new street", "up", em.find(CityInfo.class, "1616"));
        newPerson.addPhone(newPhone);
        newPerson.setAddress(newAddress);
        newPerson = facade.createPerson(newPerson);
        FullPersonDTO actual = facade.getPersonByPhone(newPhone.getNumber());
        FullPersonDTO expected = new FullPersonDTO(newPerson);
        assertEquals(expected, actual);
    }

}