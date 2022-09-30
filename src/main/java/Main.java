import dtos.FullPersonDTO;
import dtos.PersonDTO;
import entities.*;
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
//        Person person = new Person("emailadressen", "poul");
//        Hobby hobby1 = em.find(Hobby.class, )
//        Phone phone1 = new Phone("45454545", "hjemmetelefon");
//        CityInfo cityInfo1 = em.find(CityInfo.class, "3720");
//        Address address1 = new Address("some street", "th", cityInfo1);
//        person.setAddress(address1);
//        person.addHobbytoHobbySet(hobby1);
//        person.addPhone(phone1);
//        em.persist(address1);
//        em.persist(person);
//        em.persist(hobby1);
//        em.persist(phone1);
//        em.merge(person);
        em.getTransaction().commit();
        em.close();

        APIFacade facade = APIFacade.getInstance(emf);
//        FullPersonDTO fullPersonDTO = facade.getPersonByPhone("45454545");
//        System.out.println("personen hedder med nummer 45454545 hedder "+ fullPersonDTO.getFirstName());
        emf.close();
    }
}

