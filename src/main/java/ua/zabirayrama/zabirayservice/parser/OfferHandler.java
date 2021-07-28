package ua.zabirayrama.zabirayservice.parser;

import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ua.zabirayrama.zabirayservice.domain.Offer;
import ua.zabirayrama.zabirayservice.repo.CategoryRepository;
import ua.zabirayrama.zabirayservice.repo.SupplierRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class OfferHandler extends DefaultHandler {


    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;

    // List to Offer object
    private List<Offer> offersList = null;
    private Offer offers = null;
    private StringBuilder data = null;


    public OfferHandler(CategoryRepository categoryRepository, SupplierRepository supplierRepository) {

        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
    }


    public List<Offer> getOffersList() {
        return offersList;
    }

    boolean bUrl = false;
    boolean bPrice = false;
    boolean bVendorCode =false;
    boolean bCurrencyId = false;
    boolean bCategoryId = false;
    boolean bPicture = false;
    boolean bDelivery = false;
    boolean bName = false;
    boolean bDescription = false;
    boolean bVendor = false;
    boolean bCode = false;
    boolean bParam = false;

    boolean beg = false;


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("Offers")) {
            beg = true;
        }

        if(beg) {

            if (qName.equalsIgnoreCase("Offer")) {
                // create a new Employee and put it in Map
                Long suppler_id = 1L;
                String id = attributes.getValue("id");
                String available = attributes.getValue("available");
                String group_id = attributes.getValue("group_id");
                // initialize Offer object and set id attribute
                offers = new Offer();
                offers.setId(Long.parseLong(id));
                offers.setAvailable(Boolean.parseBoolean(available));
                offers.setGroup_id(group_id);

                // initialize list
                if (offersList == null)
                    offersList = new ArrayList<>();
            } else if (qName.equalsIgnoreCase("url")) {
                // set boolean values for fields
                bUrl = true;
            } else if (qName.equalsIgnoreCase("price")) {
                bPrice = true;
            } else if (qName.equalsIgnoreCase("vendorCode")) {
                bVendorCode = true;
            } else if (qName.equalsIgnoreCase("currencyId")) {
                bCurrencyId = true;
            } else if (qName.equalsIgnoreCase("categoryId")) {
                bCategoryId = true;
            } else if (qName.equalsIgnoreCase("picture")) {
                bPicture = true;
            } else if (qName.equalsIgnoreCase("delivery")) {
                bDelivery = true;
            } else if (qName.equalsIgnoreCase("name")) {
                bName = true;
            } else if (qName.equalsIgnoreCase("description")) {
                bDescription = true;
            } else if (qName.equalsIgnoreCase("vendor")) {
                bVendor = true;
            } else if (qName.equalsIgnoreCase("code")) {
                bCode = true;
            } else if (qName.equalsIgnoreCase("param")) {
                String name = attributes.getValue("name");
                offers.setParam(name);
                bParam = true;
            }


            data = new StringBuilder();

        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(beg) {
            if (bUrl) {
                //
                offers.setUrl(data.toString());
                bUrl = false;
            } else if (bPrice) {
                offers.setPrice(Double.parseDouble(data.toString()));
                bPrice = false;
            } else if (bVendorCode) {
                offers.setVendorCode(data.toString());
                bVendorCode = false;
            } else if (bCurrencyId) {
                offers.setCurrencyId(data.toString());
                bCurrencyId = false;
            } else if (bCategoryId) {
                try {
                    offers.setCategory(categoryRepository.getById(Long.parseLong(data.toString())));         //(categoryList.indexOf(Long.parseLong(data.toString())));
                    offers.setSupplier(supplierRepository.findSupplierByid(Long.parseLong("1")));
                } catch (NullPointerException e) {
                    offers.setCategory(categoryRepository.findCategoriesByid(Long.parseLong("68261641")));
                    offers.setSupplier(supplierRepository.findSupplierByid(Long.parseLong("-9999")));
                }
                bCategoryId = false;
            } else if (bPicture) {
                offers.setPicture(data.toString());
                bPicture = false;
            } else if (bDelivery) {
                offers.setDelivery(Boolean.parseBoolean(data.toString()));
                bDelivery = false;
            } else if (bName) {
                offers.setName(data.toString());
                bName = false;
            } else if (bDescription) {
                offers.setDescription(data.toString());
                bDescription = false;
            } else if (bVendor) {
                offers.setVendor(data.toString());
                bVendor = false;
            } else if (bCode) {
                offers.setCode(Long.parseLong(data.toString()));
                bCode = false;
            } else if (bParam) {
                offers.setParam(data.toString());
                bParam = false;
            }


            if (qName.equalsIgnoreCase("Offer")) {
                // add Employee object to list
                offersList.add(offers);
            }
        }

    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        if(beg) {
            data.append(new String(ch, start, length));
        }
    }
}
