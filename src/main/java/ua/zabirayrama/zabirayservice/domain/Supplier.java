package ua.zabirayrama.zabirayservice.domain;



import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "supplier")
public class Supplier implements Serializable {

    @Id
    private Long id;

    private String name;
    private String company;
    private String urlCompany;

 //   @OneToMany(mappedBy = "supplier", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
 //   private List<Offer> offers = new ArrayList<>();

    public Supplier() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getUrlCompany() {
        return urlCompany;
    }

    public void setUrlCompany(String urlCompany) {
        this.urlCompany = urlCompany;
    }

  //  public List<Offer> getOffers() {
 //       return offers;
 //   }

 //   public void setOffers(List<Offer> offers) {
 //       this.offers = offers;
//    }

    @Override
    public String toString() {
        return "Supplier{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", urlCompany='" + urlCompany + '\'' +
         //       ", offers=" + offers +
                '}';
    }
}
