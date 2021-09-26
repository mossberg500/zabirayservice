package ua.zabirayrama.zabirayservice.parser;

import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ua.zabirayrama.zabirayservice.domain.LoadOffer;
import ua.zabirayrama.zabirayservice.domain.Offer;
import ua.zabirayrama.zabirayservice.repo.CategoryRepository;
import ua.zabirayrama.zabirayservice.repo.LoadCategoryRepository;
import ua.zabirayrama.zabirayservice.repo.SupplierRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoadOfferHandler extends DefaultHandler {


    private final LoadCategoryRepository loadCategoryRepository;
    private final SupplierRepository supplierRepository;
    private String codSupp;

    // List to Offer object
    private List<LoadOffer> loadOfferList = null;
    private LoadOffer loadOffer = null;
    private StringBuilder data = null;


    public LoadOfferHandler(LoadCategoryRepository loadCategoryRepository, SupplierRepository supplierRepository) {

        this.loadCategoryRepository = loadCategoryRepository;
        this.supplierRepository = supplierRepository;

    }

    public String getCodSupp() {
        return codSupp;
    }

    public void setCodSupp(String codSupp) {
        this.codSupp = codSupp;
    }

    public List<LoadOffer> getLoadOfferList() {
        return loadOfferList;
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
                loadOffer = new LoadOffer();
                loadOffer.setId(Long.parseLong(id));
                loadOffer.setAvailable(Boolean.parseBoolean(available));
                loadOffer.setGroup_id(group_id);

                // initialize list
                if (loadOfferList == null)
                    loadOfferList = new ArrayList<>();
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
                loadOffer.setParam(name);
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
                loadOffer.setUrl(data.toString());
                bUrl = false;
            } else if (bPrice) {
                loadOffer.setPrice(Double.parseDouble(data.toString()));
                bPrice = false;
            } else if (bVendorCode) {
                loadOffer.setVendorCode(data.toString());
                bVendorCode = false;
            } else if (bCurrencyId) {
                loadOffer.setCurrencyId(data.toString());
                bCurrencyId = false;
            } else if (bCategoryId) {
                try {
                    loadOffer.setLoadCategory(loadCategoryRepository.getById(Long.parseLong(data.toString())));         //(categoryList.indexOf(Long.parseLong(data.toString())));
                    loadOffer.setSupplier(supplierRepository.findSupplierByid(Long.parseLong(this.codSupp)));
                } catch (NullPointerException e) {
                    loadOffer.setLoadCategory(loadCategoryRepository.findLoadCategoriesByid(Long.parseLong("0")));
                    loadOffer.setSupplier(supplierRepository.findSupplierByid(Long.parseLong("0")));
                }
                bCategoryId = false;
            } else if (bPicture) {
                loadOffer.setPicture(data.toString());
                bPicture = false;
            } else if (bDelivery) {
                loadOffer.setDelivery(Boolean.parseBoolean(data.toString()));
                bDelivery = false;
            } else if (bName) {
                loadOffer.setName(data.toString());
                bName = false;
            } else if (bDescription) {
                loadOffer.setDescription(data.toString());
                bDescription = false;
            } else if (bVendor) {
                loadOffer.setVendor(data.toString());
                bVendor = false;
            } else if (bCode) {
                loadOffer.setCode(Long.parseLong(data.toString()));
                bCode = false;
            } else if (bParam) {
                loadOffer.setParam(data.toString());
                bParam = false;
            }


            if (qName.equalsIgnoreCase("Offer")) {
                // add Employee object to list
                loadOfferList.add(loadOffer);
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
