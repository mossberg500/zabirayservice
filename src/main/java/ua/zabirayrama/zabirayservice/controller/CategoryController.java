package ua.zabirayrama.zabirayservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.zabirayrama.zabirayservice.domain.Category;
import ua.zabirayrama.zabirayservice.domain.Offer;
import ua.zabirayrama.zabirayservice.parser.XMLParserSAX;
import ua.zabirayrama.zabirayservice.repo.CategoryRepository;
import ua.zabirayrama.zabirayservice.util.MyLogger;

import java.util.List;

@RestController
@RequestMapping("/category") // базовый адрес
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    // получение всех данных
    @GetMapping("/all")
    public ResponseEntity<List<Category>> findAll() {

        MyLogger.showMethodName("Category: findAll() ---------------------------------------------------------------- ");

        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @GetMapping("/parser")
    public ResponseEntity savingCategory() {
        MyLogger.showMethodName("offer: saveOfferFromXML() ---------------------------------------------------------------");
        List<Category> categoryList = XMLParserSAX.xmlParserSAXCategory();
        for (Category category : categoryList) {
            categoryRepository.save(category);
        }
        return new ResponseEntity(HttpStatus.OK); // просто отправляем статус 200 (операция прошла успешно)
    }


}
