package ua.zabirayrama.zabirayservice.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Stat implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stat_category_id", referencedColumnName = "id")   //@JoinColumn(name = "priority_id", referencedColumnName = "id") // по каким полям связывать (foreign key)
    private  Category category; // = new Category();


    private Integer colOfferAll;
    private Integer colCategory;
    private Integer colOfferInCategory;





}
