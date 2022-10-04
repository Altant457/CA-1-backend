package dtos;

import entities.Hobby;
import entities.Person;
import entities.Phone;

import java.io.Serializable;
import java.util.*;

/**
 * A DTO for the {@link entities.Person} entity
 */
public class FullPersonDTO implements Serializable {
    private final Long id;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final Set<PhoneDTO> phones = new LinkedHashSet<>();
    private final AddressDTO address;
    private final Set<HobbyDTO> hobbies = new LinkedHashSet<>();

    public FullPersonDTO(Person person) {
        this.id = person.getId();
        this.email = person.getEmail();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        if (!person.getPhone().isEmpty()) {
            person.getPhone().forEach(phone -> this.phones.add(new PhoneDTO(phone)));
        }
        if (person.getAddress() != null) {
            this.address = new AddressDTO(person.getAddress().getStreet(),
                                              person.getAddress().getAdditionalInfo(),
                                              person.getAddress().getCityInfo().getZipCode(),
                                              person.getAddress().getCityInfo().getCity());
        } else {
            this.address = null;
        }
        if (!person.getHobbies().isEmpty()) {
            person.getHobbies().forEach(hobby -> this.hobbies.add(new HobbyDTO(hobby)));
        }
    }

    public static List<FullPersonDTO> getDTOList(List<Person> personList) {
        List<FullPersonDTO> fullPersonDTOList = new ArrayList<>();
        personList.forEach(person -> fullPersonDTOList.add(new FullPersonDTO(person)));
        return fullPersonDTOList;

    }

    public Long getId() {
        return id;
    }

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
        return address.street;
    }

    public String getAdditionalInfo() {
        return address.additionalInfo;
    }

    public String getZipCode() {
        return address.cityInfo.zipCode;
    }

    public String getCity() {
        return address.cityInfo.city;
    }

    public Set<HobbyDTO> getHobbies() {
        return hobbies;
    }

    public Set<PhoneDTO> getPhone() {
        return phones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullPersonDTO entity = (FullPersonDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.email, entity.email) &&
                Objects.equals(this.firstName, entity.firstName) &&
                Objects.equals(this.lastName, entity.lastName) &&
                Objects.equals(this.address.street, entity.address.street) &&
                Objects.equals(this.address.additionalInfo, entity.address.additionalInfo) &&
                Objects.equals(this.address.cityInfo.zipCode, entity.address.cityInfo.zipCode) &&
                Objects.equals(this.address.cityInfo.city, entity.address.cityInfo.city) &&
                Objects.equals(this.hobbies, entity.hobbies) &&
                Objects.equals(this.phones, entity.phones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, firstName, lastName, address.street, address.additionalInfo, address.cityInfo.zipCode, address.cityInfo.city, hobbies, phones);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "email = " + email + ", " +
                "firstName = " + firstName + ", " +
                "lastName = " + lastName + ", " +
                "addressStreet = " + address.street + ", " +
                "addressAdditionalInfo = " + address.additionalInfo + ", " +
                "addressCityInfoZipCode = " + address.cityInfo.zipCode + ", " +
                "addressCityInfoCity = " + address.cityInfo.city + ", " +
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

        public String getCategory() {
            return category;
        }

        public String getType() {
            return type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            HobbyDTO entity = (HobbyDTO) o;
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
            PhoneDTO entity = (PhoneDTO) o;
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
        private final CityInfoDTO cityInfo;

        public AddressDTO(String street, String additionalInfo, String zipCode, String city) {
            this.street = street;
            this.additionalInfo = additionalInfo;
            this.cityInfo = new CityInfoDTO(zipCode, city);
        }
    }

    public static class CityInfoDTO implements Serializable {
        private final String zipCode, city;

        public CityInfoDTO(String zipCode, String city) {
            this.zipCode = zipCode;
            this.city = city;
        }
    }
}