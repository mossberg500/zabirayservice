package ua.zabirayrama.zabirayservice.domain;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "usersZabiray")
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String photoUrl;
    private Boolean followed;
    private String fullName;
    private String status;

    public Users() {
    }

    public Long getId() {
        return id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Boolean getFollowed() {
        return followed;
    }

    public void setFollowed(Boolean followed) {
        this.followed = followed;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
