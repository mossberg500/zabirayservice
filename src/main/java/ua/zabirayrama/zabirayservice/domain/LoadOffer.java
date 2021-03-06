package ua.zabirayrama.zabirayservice.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "loadoffer")
public class LoadOffer implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;


    private Long id_offer;

    private boolean available;
    private  String group_id;
    private String url;
    private Double price;
    private String vendorCode;
    private String currencyId;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loadcategory_id", referencedColumnName = "id")   //@JoinColumn(name = "priority_id", referencedColumnName = "id") // по каким полям связывать (foreign key)
    private  LoadCategory loadCategory; // = new Category();


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    private  Supplier supplier; // =new Supplier();;


    private String picture;
    private boolean delivery;
    private String name;
    private String description;
    private String vendor;
    private long code;
    private LocalDate date;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "load_offer_param", joinColumns = @JoinColumn(name = "offer_id"))
    private List<String> param = new ArrayList<>();



    public LoadOffer() {
        Date dateNow = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.date = LocalDate.parse(dateFormat.format(dateNow));
        // this.supplier = supplier;
        //  this.category = category;

    }

    public Long getId() {
        return id_offer;
    }

    public void setId(Long id_offer) {
        this.id_offer = id_offer;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }


    public String getLoadCategory() {
        return loadCategory.getNameCategory();
    }

    public void setLoadCategory(LoadCategory loadCategory) {
        this.loadCategory = loadCategory;
    }

    public Long getLoadCategoryById() {
        return loadCategory.getId();
    }


    public Long getSupplierById() {
        return supplier.getId();
    }

    public String getSupplier() {
        return supplier.getName();
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }



    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        String str = "";
        if(description.length() > 200) {
            str = description.substring(0, 200);
        }
        this.description = str;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public List<String> getParam() {
        return param;
    }

    public void setParam(String str) {
        this.param.add(str);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoadOffer loadOffer = (LoadOffer) o;
        return //available == offer.available &&
                //     Double.compare(offer.price, price) == 0 &&
                id_offer.equals(loadOffer.id_offer) &&
                        name.equals(loadOffer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_offer, name);
    }

    @Override
    public String toString() {
        return "LoadOffer{" +
                "id=" + id +
                ", id_offer=" + id_offer +
                ", date=" + date +
                ", available=" + available +
                ", group_id='" + group_id + '\'' +
                ", url='" + url + '\'' +
                ", price=" + price +
                ", vendorCode='" + vendorCode + '\'' +
                ", currencyId='" + currencyId + '\'' +
                ", picture='" + picture + '\'' +
                ", delivery=" + delivery +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", vendor='" + vendor + '\'' +
                ", code=" + code +
                ", param=" + param +
                '}';
    }
}
