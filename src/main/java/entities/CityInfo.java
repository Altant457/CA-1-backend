package entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "city_info")
public class CityInfo {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private String zipCode;
    private String city;

    @OneToMany (mappedBy = "cityInfo")
    private Set<Address> addresses;

    public CityInfo() {
    }

    public CityInfo(String zipCode, String city) {
        this.zipCode = zipCode;
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CityInfo{" +
                "id=" + id +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}