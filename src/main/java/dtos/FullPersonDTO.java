package dtos;

import entities.Hobby;
import entities.Person;
import entities.Phone;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link entities.Person} entity
 */
public class FullPersonDTO implements Serializable {
    private final Long id;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final AddressDTO fullAddress;
    private final Set<HobbyDTO> hobbySet = new LinkedHashSet<>();
    private final Set<PhoneDTO> phones = new LinkedHashSet<>();

    public FullPersonDTO(Person person) {
        this.id = person.getId();
        this.email = person.getEmail();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.fullAddress = new AddressDTO(person.getAddress().getStreet(),
                                          person.getAddress().getAdditionalInfo(),
                                          person.getAddress().getCityInfo().getZipCode(),
                                          person.getAddress().getCityInfo().getCity());
        person.getHobbySet().forEach(hobby -> this.hobbySet.add(new HobbyDTO(hobby)));
        person.getPhone().forEach(phone -> this.phones.add(new PhoneDTO(phone)));
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

    public Set<HobbyDTO> getHobbySet() {
        return hobbySet;
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
                Objects.equals(this.fullAddress.street, entity.fullAddress.street) &&
                Objects.equals(this.fullAddress.additionalInfo, entity.fullAddress.additionalInfo) &&
                Objects.equals(this.fullAddress.zipCode, entity.fullAddress.zipCode) &&
                Objects.equals(this.fullAddress.city, entity.fullAddress.city) &&
                Objects.equals(this.hobbySet, entity.hobbySet) &&
                Objects.equals(this.phones, entity.phones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, firstName, lastName, fullAddress.street, fullAddress.additionalInfo, fullAddress.zipCode, fullAddress.city, hobbySet, phones);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "email = " + email + ", " +
                "firstName = " + firstName + ", " +
                "lastName = " + lastName + ", " +
                "addressStreet = " + fullAddress.street + ", " +
                "addressAdditionalInfo = " + fullAddress.additionalInfo + ", " +
                "addressCityInfoZipCode = " + fullAddress.zipCode + ", " +
                "addressCityInfoCity = " + fullAddress.city + ", " +
                "hobbySet = " + hobbySet + ", " +
                "phone = " + phones + ")";
    }

    /**
     * A DTO for the {@link entities.Hobby} entity
     */
    public static class HobbyDTO implements Serializable {
        private final String name;
        private final String description;

        public HobbyDTO(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public HobbyDTO(Hobby hobby) {
            this.name = hobby.getName();
            this.description = hobby.getDescription();
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