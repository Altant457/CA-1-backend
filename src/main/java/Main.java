import dtos.PersonDTO;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import facades.APIFacade;

import javax.persistence.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Open a database connection
        // (create a new database if it doesn't exist yet):
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Person person = new Person("emailadressen", "poul");
        Hobby hobby1 = new Hobby("ringridning", "hurtigt");
        Phone phone1 = new Phone("45454545", "hjemmetelefon");
        person.addHobbytoHobbySet(hobby1);
        person.addPhone(phone1);
        em.persist(person);
        em.persist(hobby1);
        em.persist(phone1);
//        em.merge(person);
        em.getTransaction().commit();
        em.close();

        APIFacade facade = APIFacade.getInstance(emf);
        PersonDTO personDTO = facade.getPersonByPhone("45454545");
        System.out.println("personen hedder med nummer 45454545 hedder "+ personDTO.getFirstName());
        emf.close();
    }
}

