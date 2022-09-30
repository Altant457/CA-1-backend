package facades;

import dtos.FullPersonDTO;
import dtos.PersonDTO;
import dtos.ZipcodesDTO;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;

import javax.enterprise.inject.Typed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.WebApplicationException;
import java.util.ArrayList;
import java.util.List;

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

    public FullPersonDTO getPersonByPhone(String phoneNumber) {
        EntityManager em = getEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN p.phone ph WHERE ph.number= :phoneNumber", Person.class);
        query.setParameter("phoneNumber", phoneNumber);
        FullPersonDTO personDTO = new FullPersonDTO(query.getSingleResult());
        em.close();
        return personDTO;

    }

    public List<PersonDTO> getPersonsByHobby(String hobbyName){
        EntityManager em = getEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN p.hobbySet h WHERE h.name = :hobbyName", Person.class);
        query.setParameter("hobbyName", hobbyName);
        return PersonDTO.getDTOList(query.getResultList());

    }

    public List<PersonDTO> getAllFromCity(String zipCode) {
        EntityManager em = getEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.address.cityInfo.zipCode = :zipCode", Person.class);
        return PersonDTO.getDTOList(query.setParameter("zipCode", zipCode).getResultList());
    }

    public int getNumberWithHobby(String hobbyname) {
        EntityManager em = getEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN p.hobbySet h WHERE h.name = :hobbyName", Person.class);
        return query.setParameter("hobbyName", hobbyname).getResultList().size();
    }

    public ZipcodesDTO getAllZipcodes() {
        EntityManager em = getEntityManager();
        TypedQuery<String> query = em.createQuery("SELECT c.zipCode FROM CityInfo c", String.class);
        return new ZipcodesDTO(query.getResultList());
    }

    public Person createPerson(String name, String email, Phone phone) {
        EntityManager em = getEntityManager();
        try {
//            if (getEmployeesByName(name).size() > 0) {
//                throw new WebApplicationException("Employee with name: " + name + " exists already.");
//            }
            Person newPerson = new Person(name, email);
            em.getTransaction().begin();
            em.persist(newPerson);
            em.getTransaction().commit();
            return newPerson;

        } finally {
            em.close();
        }
    }
}