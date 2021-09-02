package ua.zabirayrama.zabirayservice.search;

import ua.zabirayrama.zabirayservice.domain.Category;

// возможные значения, по которым можно искать категории
public class CategorySearchValues {
    private String title;

    public CategorySearchValues() {
    }

    public  CategorySearchValues(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
