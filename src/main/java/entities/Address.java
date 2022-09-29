package entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String street;
    private String additionalInfo;

    @OneToMany (mappedBy = "address")
    Set<Person> personSet = new LinkedHashSet<>();

    @ManyToOne
    private CityInfo cityInfo;

    public Address() {
    }

    public Address(String street, String additionalInfo) {
        this.street = street;
        this.additionalInfo = additionalInfo;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id) && Objects.equals(street, address.street) && Objects.equals(additionalInfo, address.additionalInfo) && Objects.equals(cityInfo, address.cityInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street, additionalInfo, cityInfo);
    }
}