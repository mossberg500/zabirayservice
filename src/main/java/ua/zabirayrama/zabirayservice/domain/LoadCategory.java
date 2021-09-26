package ua.zabirayrama.zabirayservice.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "loadcategory")
public class LoadCategory implements Serializable {
    @Id
    private Long id;

    //  @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    //  private List<Offer> offers = new ArrayList<>();

    private  int parentId;
    private String nameCategory;

    public LoadCategory() {
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


    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }



    @Override
    public String toString() {
        return "LoadCategory{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", nameCategory='" + nameCategory + '\'' +
                '}';
    }


}
