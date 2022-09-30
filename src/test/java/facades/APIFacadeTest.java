package facades;

import dtos.FullPersonDTO;
import dtos.PersonDTO;
import entities.Hobby;
import entities.Person;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

class APIFacadeTest {
    private static EntityManagerFactory emf;
    private static APIFacade facade;
    private Person p1, p2, p3;
    private PersonDTO p1DTO, p2DTO;
    private FullPersonDTO p3DTO;
    private Hobby h1, h2;

    //    @Disabled
    @BeforeAll
    public static void setUpClass()
//    void setUp()
    {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = APIFacade.getInstance(emf);
    }

    @BeforeEach
    void setUp() {

        facade = APIFacade.getInstance(emf);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Query query = em.createQuery("delete from Person p");
        query.executeUpdate();

        p1 = new Person("Testperson1", "testemail");
        p2 = new Person("Testperson2", "testemail2");
        p3 = new Person("Testperson3", "testemail3");

        p1.addHobbytoHobbySet(em.find(Hobby.class, 1L));
//        p2.addHobbytoHobbySet(em.find(Hobby.class, 2L));
//        h1 = new Hobby("hobbytest", "link", "cat","type");
//        em.persist(h1);
//        p1.addHobbytoHobbySet(h1);
//        p2.addHobbytoHobbySet(em.find(Hobby.class, 2L));

        em.persist(p1);
        em.persist(p2);
        em.persist(p3);


        em.getTransaction().commit();
        em.close();

        p1DTO = new PersonDTO(p1);
        p2DTO = new PersonDTO(p2);
//        p3DTO = new FullPersonDTO(p3);

    }

    @Test
    void getPersonByHobby() {
        List<PersonDTO> actual = facade.getPersonsByHobby(p1.getHobbySet().iterator().next().getName());
//        PersonDTO expected = p1DTO;
        assertThat(actual, containsInAnyOrder(p1DTO));

//        assertThat(actual, containsInAnyOrder(e1DTO, e2DTO, e3DTO, employeeDTO));


    }
}