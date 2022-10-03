package dtos;

import entities.Hobby;
import entities.Person;
import entities.Phone;

import java.io.Serializable;
import java.util.*;

public class FullPersonDTONoId implements Serializable {

    private final String email;
    private final String firstName;
    private final String lastName;
    private final Set<FullPersonDTONoId.PhoneDTO> phones = new LinkedHashSet<>();
    private final FullPersonDTONoId.AddressDTO fullAddress;
    private final Set<FullPersonDTONoId.HobbyDTO> hobbies = new LinkedHashSet<>();

    public FullPersonDTONoId(Person person) {
//        this.id = person.getId();
        this.email = person.getEmail();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        if (!person.getPhone().isEmpty()) {
            person.getPhone().forEach(phone -> this.phones.add(new FullPersonDTONoId.PhoneDTO(phone)));
        }
        if (person.getAddress() != null) {
            this.fullAddress = new FullPersonDTONoId.AddressDTO(person.getAddress().getStreet(),
                    person.getAddress().getAdditionalInfo(),
                    person.getAddress().getCityInfo().getZipCode(),
                    person.getAddress().getCityInfo().getCity());
        } else {
            this.fullAddress = null;
        }
        if (!person.getHobbies().isEmpty()) {
            person.getHobbies().forEach(hobby -> this.hobbies.add(new FullPersonDTONoId.HobbyDTO(hobby)));
        }
    }



    public static List<FullPersonDTO> getDTOList(List<Person> personList) {
        List<FullPersonDTO> fullPersonDTOList = new ArrayList<>();
        personList.forEach(person -> fullPersonDTOList.add(new FullPersonDTO(person)));
        return fullPersonDTOList;

    }

//    public Long getId() {
//        return id;
//    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStreet() {
        return fullAddress.street;
    }

    public String getAdditionalInfo() {
        return fullAddress.additionalInfo;
    }

    public String getZipCode() {
        return fullAddress.zipCode;
    }

    public String getCity() {
        return fullAddress.city;
    }

    public Set<FullPersonDTONoId.HobbyDTO> getHobbies() {
        return hobbies;
    }

    public Set<FullPersonDTONoId.PhoneDTO> getPhone() {
        return phones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullPersonDTONoId entity = (FullPersonDTONoId) o;
        return
//                Objects.equals(this.id, entity.id) &&
                Objects.equals(this.email, entity.email) &&
                Objects.equals(this.firstName, entity.firstName) &&
                Objects.equals(this.lastName, entity.lastName) &&
                Objects.equals(this.fullAddress.street, entity.fullAddress.street) &&
                Objects.equals(this.fullAddress.additionalInfo, entity.fullAddress.additionalInfo) &&
                Objects.equals(this.fullAddress.zipCode, entity.fullAddress.zipCode) &&
                Objects.equals(this.fullAddress.city, entity.fullAddress.city) &&
                Objects.equals(this.hobbies, entity.hobbies) &&
                Objects.equals(this.phones, entity.phones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, firstName, lastName, fullAddress.street, fullAddress.additionalInfo, fullAddress.zipCode, fullAddress.city, hobbies, phones);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
//                "id = " + id + ", " +
                "email = " + email + ", " +
                "firstName = " + firstName + ", " +
                "lastName = " + lastName + ", " +
                "addressStreet = " + fullAddress.street + ", " +
                "addressAdditionalInfo = " + fullAddress.additionalInfo + ", " +
                "addressCityInfoZipCode = " + fullAddress.zipCode + ", " +
                "addressCityInfoCity = " + fullAddress.city + ", " +
                "hobbySet = " + hobbies + ", " +
                "phone = " + phones + ")";
    }

    /**
     * A DTO for the {@link entities.Hobby} entity
     */
    public static class HobbyDTO implements Serializable {
        private final String name;
        private final String category;
        private final String type;
        private final String description;

        public HobbyDTO(String name, String category, String type, String description) {
            this.name = name;
            this.category = category;
            this.type = type;
            this.description = description;
        }

        public HobbyDTO(Hobby hobby) {
            this.name = hobby.getName();
            this.category = hobby.getCategory();
            this.type = hobby.getType();
            this.description = hobby.getWikiLink();
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FullPersonDTONoId.HobbyDTO entity = (FullPersonDTONoId.HobbyDTO) o;
            return Objects.equals(this.name, entity.name) &&
                    Objects.equals(this.description, entity.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, description);
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "name = " + name + ", " +
                    "description = " + description + ")";
        }
    }

    /**
     * A DTO for the {@link entities.Phone} entity
     */
    public static class PhoneDTO implements Serializable {
        private final String number;
        private final String description;

        public PhoneDTO(String number, String description) {
            this.number = number;
            this.description = description;
        }

        public PhoneDTO(Phone phone) {
            this.number = phone.getNumber();
            this.description = phone.getDescription();
        }

        public String getNumber() {
            return number;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FullPersonDTONoId.PhoneDTO entity = (FullPersonDTONoId.PhoneDTO) o;
            return Objects.equals(this.number, entity.number) &&
                    Objects.equals(this.description, entity.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(number, description);
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "number = " + number + ", " +
                    "description = " + description + ")";
        }
    }

    public static class AddressDTO implements Serializable {
        private final String street;
        private final String additionalInfo;
        private final String zipCode;
        private final String city;

        public AddressDTO(String street, String additionalInfo, String zipCode, String city) {
            this.street = street;
            this.additionalInfo = additionalInfo;
            this.zipCode = zipCode;
            this.city = city;
        }
    }
}
