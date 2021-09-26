package ua.zabirayrama.zabirayservice.domain;

import javax.persistence.*;
import java.io.Serializable;




@Entity
@Table(name = "location")
public class Location implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String city;
    private String country;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    private  Users users;

    public Location() {
    }

    public Long getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", users=" + users +
                '}';
    }
}
