package ua.zabirayrama.zabirayservice.search;

// возможные значения, по которым можно искать поставщика
public class SupplierSearchValues {
    private String title;

    public SupplierSearchValues() {
    }

    public SupplierSearchValues(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
