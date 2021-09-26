package ua.zabirayrama.zabirayservice.controller;


import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.zabirayrama.zabirayservice.domain.Supplier;
import ua.zabirayrama.zabirayservice.repo.SupplierRepository;
import ua.zabirayrama.zabirayservice.search.SupplierSearchValues;
import ua.zabirayrama.zabirayservice.util.MyLogger;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/supplier") // базовый адрес
@CrossOrigin(origins = "http://localhost:3000") //разрешить получать данные с данного ресурса
public class SupplierController {



    private final SupplierRepository supplierRepository;

    // автоматическое внедрение экземпляра класса через конструктор
    // не используем @Autowired ля переменной класса, т.к. "Field injection is not recommended "
    public SupplierController(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }
    // получение всех данных
    @GetMapping("/all")
    public ResponseEntity<List<Supplier>> findAll() {

        MyLogger.showMethodName("Supplier: findAll() ---------------------------------------------------------------- ");

        return ResponseEntity.ok(supplierRepository.findAll());
    }


    @PostMapping("/add")
    public ResponseEntity<Supplier> add(@RequestBody Supplier supplier){

        MyLogger.showMethodName("SupplierController: add() ---------------------------------------------------------- ");

        // проверка на обязательные параметры
        if (supplier.getId() == null && supplier.getId() == 0) {
            return new ResponseEntity("redundant param: id MUST be NOT null", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (supplier.getCompany() == null || supplier.getCompany().trim().length() == 0) {
            return new ResponseEntity("missed param: nameSupplier", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(supplierRepository.save(supplier));
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Supplier supplier){

        MyLogger.showMethodName("SupplierController: update() ---------------------------------------------------------- ");


        // проверка на обязательные параметры
        if (supplier.getId() == null || supplier.getId() == 0) {
            return new ResponseEntity("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (supplier.getCompany() == null || supplier.getCompany().trim().length() == 0) {
            return new ResponseEntity("missed param: nameSupplier", HttpStatus.NOT_ACCEPTABLE);
        }

        // save работает как на добавление, так и на обновление
        supplierRepository.save(supplier);

        return new ResponseEntity(HttpStatus.OK); // просто отправляем статус 200 (операция прошла успешно)
    }

    // параметр id передаются не в BODY запроса, а в самом URL
    @GetMapping("/id/{id}")
    public ResponseEntity<Supplier> findById(@PathVariable Long id) {

        MyLogger.showMethodName("SupplierController: findById() ---------------------------------------------------------- ");


        Supplier supplier = null;

        // можно обойтись и без try-catch, тогда будет возвращаться полная ошибка (stacktrace)
        // здесь показан пример, как можно обрабатывать исключение и отправлять свой текст/статус
        try{
            supplier = supplierRepository.findById(id).get();
        }catch (NoSuchElementException e){ // если объект не будет найден
            e.printStackTrace();
            return new ResponseEntity("id="+id+" not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return  ResponseEntity.ok(supplier);
    }


    // параметр id передаются не в BODY запроса, а в самом URL
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {

        MyLogger.showMethodName("SupplierController: delete() ---------------------------------------------------------- ");


        // можно обойтись и без try-catch, тогда будет возвращаться полная ошибка (stacktrace)
        // здесь показан пример, как можно обрабатывать исключение и отправлять свой текст/статус
        try {
            supplierRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            e.printStackTrace();
            return new ResponseEntity("id="+id+" not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity(HttpStatus.OK); // просто отправляем статус 200 (операция прошла успешно)
    }

    // поиск по любым параметрам SupplierSearchValues
    @PostMapping("/search")
    public ResponseEntity<List<Supplier>> search(@RequestBody SupplierSearchValues supplierSearchValues){

        MyLogger.showMethodName("SupplierController: search() ---------------------------------------------------------- ");


        // если вместо текста будет пусто или null - вернутся все категории
        return ResponseEntity.ok(supplierRepository.findByTitle(supplierSearchValues.getTitle()));
    }



}
