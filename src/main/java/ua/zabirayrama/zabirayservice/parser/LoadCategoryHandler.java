package ua.zabirayrama.zabirayservice.parser;


import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ua.zabirayrama.zabirayservice.domain.Category;
import ua.zabirayrama.zabirayservice.domain.LoadCategory;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoadCategoryHandler extends DefaultHandler {
    private List<LoadCategory> loadCategoryList = null;
    private LoadCategory loadCategory = null;
    private StringBuilder data1 = null;

    public List<LoadCategory> getLoadCategoryList() {
        return loadCategoryList;
    }

    private boolean bName = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {



        if (qName.equalsIgnoreCase("Category")) {
            String id = attributes.getValue("id");
            String parentId = "";
            parentId = attributes.getValue("parentId");
            loadCategory = new LoadCategory();
            loadCategory.setId(Long.parseLong(id));
            try {
                loadCategory.setParentId(Integer.parseInt(parentId));
            } catch (Exception e) {
                System.out.println("Присваиваем  category.setParentId = 0 ");
                loadCategory.setParentId(0);
            }
            // initialize list
            if (loadCategoryList == null)
                loadCategoryList = new ArrayList<>();

        }else if (qName.equalsIgnoreCase("url")) {

        }
        data1 = new StringBuilder();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("Category")) {
            loadCategory.setNameCategory(data1.toString());
            // add Employee object to list
            loadCategoryList.add(loadCategory);
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        data1.append(new String(ch, start, length));
    }
}
