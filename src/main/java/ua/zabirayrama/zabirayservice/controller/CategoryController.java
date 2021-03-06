package ua.zabirayrama.zabirayservice.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.zabirayrama.zabirayservice.domain.Category;
import ua.zabirayrama.zabirayservice.parser.XMLParserSAX;
import ua.zabirayrama.zabirayservice.repo.CategoryRepository;
import ua.zabirayrama.zabirayservice.search.CategorySearchValues;
import ua.zabirayrama.zabirayservice.util.MyLogger;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/category") // базовый адрес
@CrossOrigin(origins = "http://localhost:3000") //разрешить получать данные с данного ресурса
public class CategoryController {

    private final CategoryRepository categoryRepository;

    // автоматическое внедрение экземпляра класса через конструктор
    // не используем @Autowired ля переменной класса, т.к. "Field injection is not recommended "
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    // получение всех данных
    @GetMapping("/all")
    public ResponseEntity<List<Category>> findAll() {

        MyLogger.showMethodName("Category: findAll() ---------------------------------------------------------------- ");

        return ResponseEntity.ok(categoryRepository.findAll());
    }


    @PostMapping("/add")
    public ResponseEntity<Category> add(@RequestBody Category category){

        MyLogger.showMethodName("CategoryController: add() ---------------------------------------------------------- ");

        // проверка на обязательные параметры
        if (category.getId() == null && category.getId() == 0) {
            return new ResponseEntity("redundant param: id MUST be NOT null", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (category.getNameCategory() == null || category.getNameCategory().trim().length() == 0) {
            return new ResponseEntity("missed param: nameCategory", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(categoryRepository.save(category));
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Category category){

        MyLogger.showMethodName("CategoryController: update() ---------------------------------------------------------- ");


        // проверка на обязательные параметры
        if (category.getId() == null || category.getId() == 0) {
            return new ResponseEntity("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (category.getNameCategory() == null || category.getNameCategory().trim().length() == 0) {
            return new ResponseEntity("missed param: nameCategory", HttpStatus.NOT_ACCEPTABLE);
        }

        // save работает как на добавление, так и на обновление
        categoryRepository.save(category);

        return new ResponseEntity(HttpStatus.OK); // просто отправляем статус 200 (операция прошла успешно)
    }

    // параметр id передаются не в BODY запроса, а в самом URL
    @GetMapping("/id/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {

        MyLogger.showMethodName("CategoryController: findById() ---------------------------------------------------------- ");


        Category category = null;

        // можно обойтись и без try-catch, тогда будет возвращаться полная ошибка (stacktrace)
        // здесь показан пример, как можно обрабатывать исключение и отправлять свой текст/статус
        try{
            category = categoryRepository.findById(id).get();
        }catch (NoSuchElementException e){ // если объект не будет найден
            e.printStackTrace();
            return new ResponseEntity("id="+id+" not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return  ResponseEntity.ok(category);
    }


    // параметр id передаются не в BODY запроса, а в самом URL
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {

        MyLogger.showMethodName("CategoryController: delete() ---------------------------------------------------------- ");


        // можно обойтись и без try-catch, тогда будет возвращаться полная ошибка (stacktrace)
        // здесь показан пример, как можно обрабатывать исключение и отправлять свой текст/статус
        try {
            categoryRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            e.printStackTrace();
            return new ResponseEntity("id="+id+" not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity(HttpStatus.OK); // просто отправляем статус 200 (операция прошла успешно)
    }

    // поиск по любым параметрам CategorySearchValues
    @PostMapping("/search")
    public ResponseEntity<List<Category>> search(@RequestBody CategorySearchValues categorySearchValues){

        MyLogger.showMethodName("CategoryController: search() ---------------------------------------------------------- ");


        // если вместо текста будет пусто или null - вернутся все категории
        return ResponseEntity.ok(categoryRepository.findByTitle(categorySearchValues.getTitle()));
    }



}
