import entities.Hobby;
import entities.Person;

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
        person.addHobbytoHobbySet(hobby1);
        em.persist(person);
        em.persist(hobby1);
//        em.merge(person);
        em.getTransaction().commit();
        em.close();
        emf.close();
    }
}

