package ua.zabirayrama.zabirayservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.zabirayrama.zabirayservice.domain.LoadCategory;
import ua.zabirayrama.zabirayservice.domain.Supplier;
import ua.zabirayrama.zabirayservice.parser.XMLParserSAX;
import ua.zabirayrama.zabirayservice.repo.LoadCategoryRepository;
import ua.zabirayrama.zabirayservice.repo.SupplierRepository;
import ua.zabirayrama.zabirayservice.util.MyLogger;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/loadcategory") // базовый адрес
@CrossOrigin(origins = "http://localhost:3000") //разрешить получать данные с данного ресурса
public class LoadCategoryController {

    private final LoadCategoryRepository loadCategoryRepository;
    private final SupplierRepository supplierRepository;

    public LoadCategoryController(LoadCategoryRepository loadCategoryRepository, SupplierRepository supplierRepository) {
        this.loadCategoryRepository = loadCategoryRepository;
        this.supplierRepository = supplierRepository;
    }

    @GetMapping("/parser")
    public ResponseEntity savingCategory() {
        MyLogger.showMethodName("offer: saveOfferFromXML() ---------------------------------------------------------------");
        List<Supplier> suppliers = new ArrayList<>();
        suppliers = supplierRepository.findAll();
        for (Supplier supplier : suppliers) {
            if (supplier.getId() != 0) {

                List<LoadCategory> loadCategoryList = XMLParserSAX.xmlParserSAXCategory(supplier.getUrlCompany());
                for (LoadCategory loadCategory : loadCategoryList) {
                    loadCategoryRepository.save(loadCategory);
                }
            }
        }
        return new ResponseEntity(HttpStatus.OK); // просто отправляем статус 200 (операция прошла успешно)
    }
}
