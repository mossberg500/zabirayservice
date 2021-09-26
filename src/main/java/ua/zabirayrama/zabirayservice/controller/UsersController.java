package ua.zabirayrama.zabirayservice.controller;


import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.zabirayrama.zabirayservice.domain.Users;
import ua.zabirayrama.zabirayservice.repo.UsersRepository;
import ua.zabirayrama.zabirayservice.util.MyLogger;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users") // базовый адрес
@CrossOrigin(origins = "http://localhost:3000") //разрешить получать данные с данного ресурса
public class UsersController {



    private final UsersRepository usersRepository;

    // автоматическое внедрение экземпляра класса через конструктор
    // не используем @Autowired ля переменной класса, т.к. "Field injection is not recommended "
    public UsersController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
    // получение всех данных
    @GetMapping("/all")
    public ResponseEntity<List<Users>> findAll() {

        MyLogger.showMethodName("Users: findAll() ---------------------------------------------------------------- ");

        return ResponseEntity.ok(usersRepository.findAll());
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
        return ResponseEntity.ok(usersRepository.save(users));
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
        usersRepository.save(users);

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
            users = usersRepository.findById(id).get();
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
            usersRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            e.printStackTrace();
            return new ResponseEntity("id="+id+" not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity(HttpStatus.OK); // просто отправляем статус 200 (операция прошла успешно)
    }




}
