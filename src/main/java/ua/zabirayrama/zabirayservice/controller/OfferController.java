package ua.zabirayrama.zabirayservice.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.zabirayrama.zabirayservice.domain.Offer;
import ua.zabirayrama.zabirayservice.repo.CategoryRepository;
import ua.zabirayrama.zabirayservice.repo.SupplierRepository;
import ua.zabirayrama.zabirayservice.search.OfferSearchValues;
import ua.zabirayrama.zabirayservice.service.OfferService;
import ua.zabirayrama.zabirayservice.util.MyLogger;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/offer") // базовый адрес
@CrossOrigin(origins = "http://localhost:4200") //разрешить получать данные с данного ресурса
public class OfferController {
    private final OfferService offerService; // сервис для доступа к данным (напрямую к репозиториям не обращаемся)

    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;


    // автоматическое внедрение экземпляра класса через конструктор
    // не используем @Autowired ля переменной класса, т.к. "Field injection is not recommended "
    public OfferController(OfferService offerService, CategoryRepository categoryRepository, SupplierRepository supplierRepository) {
        this.offerService = offerService;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
    }


    // получение всех данных
    @GetMapping("/all")
    public ResponseEntity<List<Offer>> findAll() {

        MyLogger.showMethodName("offer: findAll() ---------------------------------------------------------------- ");

        return ResponseEntity.ok(offerService.findAll());
    }

    // добавление
    @PostMapping("/add")
    public ResponseEntity<Offer> add(@RequestBody Offer offer) {

        MyLogger.showMethodName("offer: add() ---------------------------------------------------------------- ");

        // проверка на обязательные параметры
        if (offer.getId() != null && offer.getId() != 0) {
            // id создается автоматически в БД (autoincrement), поэтому его передавать не нужно, иначе может быть конфликт уникальности значения
            return new ResponseEntity("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (offer.getName() == null || offer.getName().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(offerService.add(offer)); // возвращаем созданный объект со сгенерированным id

    }


    // обновление
    @PutMapping("/update")
    public ResponseEntity<Offer> update(@RequestBody Offer offer) {

        MyLogger.showMethodName("offer: update() ---------------------------------------------------------------- ");

        // проверка на обязательные параметры
        if (offer.getId() == null || offer.getId() == 0) {
            return new ResponseEntity("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (offer.getName() == null || offer.getName().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }


        // save работает как на добавление, так и на обновление
        offerService.update(offer);

        return new ResponseEntity(HttpStatus.OK); // просто отправляем статус 200 (операция прошла успешно)

    }


    // удаление по id
    // параметр id передаются не в BODY запроса, а в самом URL
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {

        MyLogger.showMethodName("offer: delete() ---------------------------------------------------------------- ");


        // можно обойтись и без try-catch, тогда будет возвращаться полная ошибка (stacktrace)
        // здесь показан пример, как можно обрабатывать исключение и отправлять свой текст/статус
        try {
            offerService.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK); // просто отправляем статус 200 (операция прошла успешно)
    }


    // получение объекта по id
    @GetMapping("/id/{id}")
    public ResponseEntity<Offer> findById(@PathVariable Long id) {

        MyLogger.showMethodName("offer: findById() ---------------------------------------------------------------- ");

        Offer offer = null;

        // можно обойтись и без try-catch, тогда будет возвращаться полная ошибка (stacktrace)
        // здесь показан пример, как можно обрабатывать исключение и отправлять свой текст/статус
        try {
            offer = offerService.findById(id);
        } catch (NoSuchElementException e) { // если объект не будет найден
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(offer);
    }


    // поиск по любым параметрам
    // OfferSearchValues содержит все возможные параметры поиска
    @PostMapping("/search")
    public ResponseEntity<Page<Offer>> search(@RequestBody OfferSearchValues offerSearchValues) {

        MyLogger.showMethodName("offer: search() ---------------------------------------------------------------- ");


        // исключить NullPointerException
        String text = offerSearchValues.getName() != null ? offerSearchValues.getName() : null;

        // конвертируем Boolean в Integer
        Double price = offerSearchValues.getPrice() != null ? offerSearchValues.getPrice() : null;

   //     Long priorityId = offerSearchValues.getPriorityId() != null ? offerSearchValues.getPriorityId() : null;
   //     Long categoryId = offerSearchValues.getCategoryId() != null ? offerSearchValues.getCategoryId() : null;

        String sortColumn = offerSearchValues.getSortColumn() != null ? offerSearchValues.getSortColumn() : null;
        String sortDirection = offerSearchValues.getSortDirection() != null ? offerSearchValues.getSortDirection() : null;

        Integer pageNumber = offerSearchValues.getPageNumber() != null ? offerSearchValues.getPageNumber() : null;
        Integer pageSize = offerSearchValues.getPageSize() != null ? offerSearchValues.getPageSize() : null;

        Sort.Direction direction = sortDirection == null || sortDirection.trim().length() == 0 || sortDirection.trim().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        // подставляем все значения

        // объект сортировки
        Sort sort = Sort.by(direction, sortColumn);

        // объект постраничности
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        // результат запроса с постраничным выводом
        Page result = offerService.findByParams(text, price, pageRequest);

        // результат запроса
        return ResponseEntity.ok(result);

    }

    @GetMapping("/parser")
    public ResponseEntity saveOfferFromXML() {
        MyLogger.showMethodName("offer: saveOfferFromXML() ---------------------------------------------------------------- ");

        offerService.savingOffer(categoryRepository, supplierRepository);

        return new ResponseEntity(HttpStatus.OK); // просто отправляем статус 200 (операция прошла успешно)
    }


}
