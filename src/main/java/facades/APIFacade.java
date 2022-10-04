package facades;

import dtos.*;
import entities.*;


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
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN p.phones ph WHERE ph.number= :phoneNumber", Person.class);
        query.setParameter("phoneNumber", phoneNumber);
        FullPersonDTO personDTO = new FullPersonDTO(query.getSingleResult());
        em.close();
        return personDTO;

    }

    public List<PersonDTO> getPersonsByHobby(String hobbyName){
        EntityManager em = getEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN p.hobbies h WHERE h.name = :hobbyName", Person.class);
        query.setParameter("hobbyName", hobbyName);
        List<PersonDTO> personDTOList = PersonDTO.getDTOList(query.getResultList());
        em.close();
        return personDTOList;
    }

    public List<PersonDTO> getAllFromCity(String zipCode) {
        EntityManager em = getEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.address.cityInfo.zipCode = :zipCode", Person.class);
        List<PersonDTO> personDTOList = PersonDTO.getDTOList(query.setParameter("zipCode", zipCode).getResultList());
        em.close();
        return personDTOList;

    }

    public int getNumberWithHobby(String hobbyname) {
        EntityManager em = getEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN p.hobbies h WHERE h.name = :hobbyName", Person.class);
        int count = query.setParameter("hobbyName", hobbyname).getResultList().size();
        em.close();
        return count;

    }

    public ZipcodesDTO getAllZipcodes() {
        EntityManager em = getEntityManager();
        TypedQuery<String> query = em.createQuery("SELECT c.zipCode FROM CityInfo c", String.class);
        ZipcodesDTO zipcodes = new ZipcodesDTO(query.getResultList());
        em.close();
        return zipcodes;
    }

    // newPerson contains fName, lName and email, according to api doc
    // maybe they should have a phone number as well? - Marcus
    public Person createPerson(Person newPerson) {
        EntityManager em = getEntityManager();
        try {
            List<Person> personList = em.createQuery("SELECT p FROM Person p WHERE p.email = :email", Person.class)
                            .setParameter("email", newPerson.getEmail()).getResultList();
            if (personList.size() > 0) {
                throw new WebApplicationException(String.format("Person with email \"%s\" exists already.", newPerson.getEmail()));
            }
            em.getTransaction().begin();
            updateAddress(newPerson, em);
            em.persist(newPerson);
            newPerson.getPhone().forEach(phone -> {
                phone.setPerson(newPerson);
            });
            em.getTransaction().commit();
            return newPerson;
        } finally {
            em.close();
        }
    }

    public Person editPerson(Person person) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            updateAddress(person, em);
            em.merge(person);
            em.getTransaction().commit();
            return person;
        } finally {
            em.close();
        }
    }

    public List<HobbyDTO> getAllHobbies() {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            List<Hobby> hobbies = em.createQuery("SELECT h FROM Hobby h", Hobby.class)
                            .getResultList();
            em.getTransaction().commit();
            return HobbyDTOs.makeDTOlist(hobbies);
        } finally {
            em.close();
        }
    }

    private void updateAddress(Person newPerson, EntityManager em) {
        List<Address> addresses = em.createQuery("SELECT a FROM Address a WHERE a.street = :street AND a.cityInfo.zipCode = :zipCode", Address.class)
                .setParameter("street", newPerson.getAddress().getStreet())
                .setParameter("zipCode", newPerson.getAddress().getCityInfo().getZipCode())
                .getResultList();
        if (addresses.size() > 0) {
            newPerson.setAddress(addresses.get(0));
            em.merge(addresses.get(0));
        } else {
            em.persist(newPerson.getAddress());
        }
    }

}