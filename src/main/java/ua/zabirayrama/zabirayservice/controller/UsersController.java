package ua.zabirayrama.zabirayservice.controller;


import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.zabirayrama.zabirayservice.domain.Offer;
import ua.zabirayrama.zabirayservice.domain.Users;
import ua.zabirayrama.zabirayservice.repo.UsersRepository;
import ua.zabirayrama.zabirayservice.service.UsersService;
import ua.zabirayrama.zabirayservice.util.MyLogger;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users") // базовый адрес
@CrossOrigin(origins = "http://localhost:3000") //разрешить получать данные с данного ресурса
public class UsersController {



    private final UsersService usersService;

    // автоматическое внедрение экземпляра класса через конструктор
    // не используем @Autowired ля переменной класса, т.к. "Field injection is not recommended "
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }
    // получение всех данных
    @GetMapping("/all")
    public ResponseEntity<List<Users>> findAll() {

        MyLogger.showMethodName("Users: findAll() ---------------------------------------------------------------- ");

        return ResponseEntity.ok(usersService.findAll());
    }


    @PostMapping("/add")
    public ResponseEntity<Users> add(@RequestBody Users users){

        MyLogger.showMethodName("UsersController: add() ---------------------------------------------------------- ");

        // проверка на обязательные параметры
        if (users.getId() == null && users.getId() == 0) {
            return new ResponseEntity("redundant param: id MUST be NOT null", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (users.getFullName() == null || users.getFullName().trim().length() == 0) {
            return new ResponseEntity("missed param: nameUsers", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(usersService.add(users));
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Users users){

        MyLogger.showMethodName("UsersController: update() ---------------------------------------------------------- ");


        // проверка на обязательные параметры
        if (users.getId() == null || users.getId() == 0) {
            return new ResponseEntity("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (users.getFullName() == null || users.getFullName().trim().length() == 0) {
            return new ResponseEntity("missed param: nameUsers", HttpStatus.NOT_ACCEPTABLE);
        }

        // save работает как на добавление, так и на обновление
        usersService.add(users);

        return new ResponseEntity(HttpStatus.OK); // просто отправляем статус 200 (операция прошла успешно)
    }

    // параметр id передаются не в BODY запроса, а в самом URL
    @GetMapping("/id/{id}")
    public ResponseEntity<Users> findById(@PathVariable Long id) {

        MyLogger.showMethodName("UsersController: findById() ---------------------------------------------------------- ");


        Users users = null;

        // можно обойтись и без try-catch, тогда будет возвращаться полная ошибка (stacktrace)
        // здесь показан пример, как можно обрабатывать исключение и отправлять свой текст/статус
        try{
            users = usersService.findById(id);
        }catch (NoSuchElementException e){ // если объект не будет найден
            e.printStackTrace();
            return new ResponseEntity("id="+id+" not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return  ResponseEntity.ok(users);
    }


    // параметр id передаются не в BODY запроса, а в самом URL
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {

        MyLogger.showMethodName("UsersController: delete() ---------------------------------------------------------- ");


        // можно обойтись и без try-catch, тогда будет возвращаться полная ошибка (stacktrace)
        // здесь показан пример, как можно обрабатывать исключение и отправлять свой текст/статус
        try {
            usersService.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            e.printStackTrace();
            return new ResponseEntity("id="+id+" not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity(HttpStatus.OK); // просто отправляем статус 200 (операция прошла успешно)
    }


    @PostMapping("/get")
    public ResponseEntity<Page<Users>> get(@RequestParam Integer pageNumber,
                                           @RequestParam Integer pageSize
                              ) {
        Integer pageN = pageNumber != null ? pageNumber-1 : 0;
        Integer pageS = pageSize != null ? pageSize : 5;

        PageRequest pageRequest = PageRequest.of(pageN, pageS);
        Page result = usersService.findByPages(pageRequest);

        return ResponseEntity.ok(result);
    }
}
