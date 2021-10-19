package ua.zabirayrama.zabirayservice.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;


@Entity
public class Price implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_offer", referencedColumnName = "id_offer")
    private Offer offer;


    private Long Price;
    private Date date;

    public Price() {
    }

    public Long getId() {
        return id;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Long getPrice() {
        return Price;
    }

    public void setPrice(Long price) {
        Price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", offer=" + offer +
                ", Price=" + Price +
                ", date=" + date +
                '}';
    }
}
