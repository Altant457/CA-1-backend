package entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;



    @Entity
    public class CityInfo {
//        private static final long serialVersionUID = 1L;
        @Id
        @Column(length = 4)
        private String zipCode;
        @Column(length=35)
        private String city;

//@Entity
//@Table(name = "cityinfo")
//public class CityInfo {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", nullable = false)
//    private Long id;
//    private String zipCode;
//    private String city;

    @OneToMany (mappedBy = "cityInfo")
    private Set<Address> addresses= new LinkedHashSet<>();

    public CityInfo() {
    }

    public CityInfo(String zipCode) {
        this.zipCode = zipCode;
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

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    @Override
    public String toString() {
        return "CityInfo{" +
//                "id=" + id +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}