package ua.zabirayrama.zabirayservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.zabirayrama.zabirayservice.repo.LoadCategoryRepository;
import ua.zabirayrama.zabirayservice.repo.SupplierRepository;
import ua.zabirayrama.zabirayservice.service.LoadOfferService;
import ua.zabirayrama.zabirayservice.util.MyLogger;

@RestController
@RequestMapping("/loadoffer") // базовый адрес
@CrossOrigin(origins = "http://localhost:3000") //разрешить получать данные с данного ресурса
public class LoadOfferController {
    private final LoadOfferService loadOfferService; // сервис для доступа к данным (напрямую к репозиториям не обращаемся)
    private final LoadCategoryRepository loadCategoryRepository;
    private final SupplierRepository supplierRepository;




    // автоматическое внедрение экземпляра класса через конструктор
    // не используем @Autowired ля переменной класса, т.к. "Field injection is not recommended "
    public LoadOfferController(LoadOfferService loadOfferService, LoadCategoryRepository loadCategoryRepository, SupplierRepository supplierRepository) {
        this.loadOfferService = loadOfferService;
        this.loadCategoryRepository = loadCategoryRepository;
        this.supplierRepository = supplierRepository;
    }

    @GetMapping("/parser")
    public ResponseEntity saveOfferFromXML() {
        MyLogger.showMethodName("offer: saveOfferFromXML() ---------------------------------------------------------------- ");

        loadOfferService.savingLoadOffer(loadCategoryRepository, supplierRepository);

        return new ResponseEntity(HttpStatus.OK); // просто отправляем статус 200 (операция прошла успешно)
    }


}
