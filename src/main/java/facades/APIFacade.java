package facades;

import dtos.FullPersonDTO;
import dtos.PersonDTO;
import entities.Person;

import javax.enterprise.inject.Typed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

public class APIFacade {
    private static APIFacade instance;
    private static EntityManagerFactory emf;

    private APIFacade() {
    }


    public static APIFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new APIFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // create person

    //TODO String phoneNumber
//    public PersonDTO getPersonByPhone(Long phoneNumber) {
    public FullPersonDTO getPersonByPhone(String phoneNumber) {
        EntityManager em = emf.createEntityManager();
        //TODO join med telfonnummer osv
//        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.id = ?1", Person.class);
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN p.phone ph WHERE ph.number= :phoneNumber", Person.class);
        query.setParameter("phoneNumber", phoneNumber);
        FullPersonDTO personDTO = new FullPersonDTO(query.getSingleResult());
        em.close();
        return personDTO;

    }
}