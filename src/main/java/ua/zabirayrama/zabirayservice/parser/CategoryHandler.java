package ua.zabirayrama.zabirayservice.parser;


import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ua.zabirayrama.zabirayservice.domain.Category;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryHandler extends DefaultHandler {
    private List<Category> categoryList = null;
    private Category category = null;
    private StringBuilder data1 = null;

    public List<Category> getCategoryList() {
        return categoryList;
    }

    private boolean bName = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {



        if (qName.equalsIgnoreCase("Category")) {
            String id = attributes.getValue("id");
            String parentId = "";
            parentId = attributes.getValue("parentId");
            category = new Category();
            category.setId(Long.parseLong(id));
            try {
                category.setParentId(Integer.parseInt(parentId));
            } catch (Exception e) {
                System.out.println("Присваиваем  category.setParentId = 0 ");
                category.setParentId(0);
            }
            // initialize list
            if (categoryList == null)
                categoryList = new ArrayList<>();

        }else if (qName.equalsIgnoreCase("url")) {

        }
        data1 = new StringBuilder();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("Category")) {
            category.setNameCategory(data1.toString());
            // add Employee object to list
            categoryList.add(category);
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        data1.append(new String(ch, start, length));
    }
}
