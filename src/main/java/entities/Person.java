package entities;

import dtos.FullPersonDTO;
import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String email;
    private String firstName;
    private String lastName;

    @ManyToOne
    private Address address;

    @ManyToMany
    private Set<Hobby> hobbySet = new LinkedHashSet<>();

    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST)
    private Set<Phone> phone = new LinkedHashSet<>();

    public Person() {
    }

    public Person(FullPersonDTO fullPersonDTO) {
        this.email = fullPersonDTO.getEmail();
        this.firstName = fullPersonDTO.getFirstName();
        this.lastName = fullPersonDTO.getLastName();
        this.address = new Address(fullPersonDTO.getStreet(),
                                   fullPersonDTO.getAdditionalInfo(),
                                new CityInfo(fullPersonDTO.getZipCode(), fullPersonDTO.getCity()));
        fullPersonDTO.getHobbies().forEach(hobbyDTO -> {
            this.addHobbytoHobbySet(new Hobby(hobbyDTO.getName(),
                    hobbyDTO.getDescription(),
                    hobbyDTO.getCategory(),
                    hobbyDTO.getType()));
        });
        fullPersonDTO.getPhone().forEach(phoneDTO -> {
            this.addPhone(new Phone(phoneDTO.getNumber(), phoneDTO.getDescription()));
        });
    }

    public Person(String email, String firstName) {
        this.email = email;
        this.firstName = firstName;
    }

    public Person(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addHobbytoHobbySet(Hobby hobby){
        this.hobbySet.add(hobby);
        hobby.getPersonSet().add(this);

    }

    public void addPhone(Phone phone){
        this.phone.add(phone);
        phone.setPerson(this);

    }

    public Address getAddress() {
        return address;
    }

    public Set<Phone> getPhone() {
        return phone;
    }

//    public void setPhone(Set<Phone> phone) {
//        this.phone = phone;
//    }
    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Hobby> getHobbies() {
        return hobbySet;
    }

    public void setHobbySet(Set<Hobby> hobbySet) {
        this.hobbySet = hobbySet;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address=" + address +
                ", hobbySet=" + hobbySet +
                ", phone=" + phone +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(email, person.email) && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(address, person.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, firstName, lastName, address);
    }
}