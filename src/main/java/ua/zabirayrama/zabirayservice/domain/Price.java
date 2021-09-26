package ua.zabirayrama.zabirayservice.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;


@Entity
public class Price implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String currencyId;
    private Date date;

    public Price() {
    }

    public Long getId() {
        return id;
    }



    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
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
                ", currencyId='" + currencyId + '\'' +
                ", date=" + date +
                '}';
    }
}
