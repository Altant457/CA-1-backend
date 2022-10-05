package facades;

import dtos.*;
import entities.Address;
import entities.Hobby;
import entities.Person;
import entities.Phone;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.ws.rs.WebApplicationException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN p.hobbySet h WHERE h.name = :hobbyName", Person.class);
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
            newPerson.getPhone().forEach(em::persist);
            updateAddress(newPerson, em);
            em.persist(newPerson);
            em.getTransaction().commit();
            return newPerson;
        } finally {
            em.close();
        }
    }

    public FullPersonDTO editPerson(FullPersonDTO fullPersonDTO) {
        EntityManager em = getEntityManager();
        Person editedPerson = new Person(fullPersonDTO);
//        System.out.println("i editPerson i facaden er personobjektet: " + editedPerson);
        try {
            em.getTransaction().begin();
            updateAddress(editedPerson, em);
            editedPerson.setPhone(updatePhone(editedPerson, editedPerson.getPhone(), em));
            em.merge(editedPerson);
            em.getTransaction().commit();
            return new FullPersonDTO(editedPerson);
        } finally {
            em.close();
        }
    }

    private Set<Phone> updatePhone(Person newPerson, Set<Phone> newPhones, EntityManager em) {
        newPhones.forEach(phone -> {
            try {
                Phone oldPhone = em.createQuery("SELECT ph FROM Phone ph WHERE ph.person.id = :id AND ph.number = :number", Phone.class)
                        .setParameter("id", newPerson.getId())
                        .setParameter("number", phone.getNumber())
                        .getSingleResult();
                phone.setId(oldPhone.getId());
                em.merge(phone);
            } catch (Exception e) {
                em.persist(phone);
            }
        });
        return newPhones;
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


    public Person delete(Long id) throws EntityNotFoundException {
        EntityManager em = getEntityManager();
        Person p = em.find(Person.class, id);
        if (p == null)
            throw new EntityNotFoundException("Could not remove Person with id: "+id);
        em.getTransaction().begin();
        p.getHobbies().forEach(hobby -> {
            hobby.getPersonSet().remove(p);
        em.merge(hobby);
        });
        p.getPhone().forEach(phone -> {
            em.remove(phone);
        });
        em.remove(p);
        em.getTransaction().commit();
        return p;
    }


    //public void deletePerson(FullPersonDTO fullPersonDTO) {
    public void deletePerson(Long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            //em.remove(fullPersonDTO);
            TypedQuery<Person> query = em.createQuery("DELETE FROM Person p WHERE p.id = :id", Person.class);
            query.setParameter("id", id);
            //em.merge(fullPersonDTO);
            em.getTransaction().commit();
            //Return number of affected rows? or string with info?
        } finally {
            em.close();
        }
    }


}