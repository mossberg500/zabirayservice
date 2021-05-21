package ua.zabirayrama.zabirayservice.domain;




import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
public class Category implements Serializable {
    @Id
    private Long id;

  //  @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  //  private List<Offer> offers = new ArrayList<>();

    private  int parentId;
    private String nameCategory;

    public Category() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

 //   public List<Offer> getOffers() {
 //       return offers;
 //   }

 //   public void setOffers(List<Offer> offers) {
 //       this.offers = offers;
 //   }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }



    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
       //         ", offers=" + offers +
                ", parentId=" + parentId +
                ", nameCategory='" + nameCategory + '\'' +
                '}';
    }


}
