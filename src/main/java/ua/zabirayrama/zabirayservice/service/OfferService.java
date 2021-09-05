package ua.zabirayrama.zabirayservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.zabirayrama.zabirayservice.domain.Offer;
import ua.zabirayrama.zabirayservice.parser.XMLParserSAX;
import ua.zabirayrama.zabirayservice.repo.CategoryRepository;
import ua.zabirayrama.zabirayservice.repo.OfferRepository;
import ua.zabirayrama.zabirayservice.repo.SupplierRepository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;


// всегда нужно создавать отдельный класс Service для доступа к данным, даже если кажется,
// что мало методов или это все можно реализовать сразу в контроллере
// Такой подход полезен для будущих доработок и правильной архитектуры (особенно, если работаете с транзакциями)
@Service

// все методы класса должны выполниться без ошибки, чтобы транзакция завершилась
// если в методе возникнет исключение - все выполненные операции откатятся (Rollback)
@Transactional
public class OfferService {

    private final OfferRepository offerRepository;

    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public List<Offer> findAll() {
        return offerRepository.findAll();
    }

    public Offer add(Offer offer) {
        return offerRepository.save(offer); // метод save обновляет или создает новый объект, если его не было
    }

    public Offer update(Offer offer) {
        return offerRepository.save(offer);
    }

    public void deleteById(Long id) {
        offerRepository.deleteById(id);
    }

    public Offer findById(Long id){
        return offerRepository.findById(id).get(); // т.к. возвращается Optional - нужно получить объект методом get()
    }

    public Page findByParams(String name, Double price, Long categoryId, Long supplierId, PageRequest paging){
        System.out.println("categoryId  ===>" + categoryId);
        return offerRepository.findByParams(name, price, categoryId, supplierId,  paging);
    }

    public List<Offer> findByLists(String name, Double price){
        return offerRepository.findByList(name, price);
    }



    public List<Offer> selectUpDateOffer() {
        Date dateNow = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateFormat.format(dateNow));
        return offerRepository.selectUpDate(date);
    }


    @Transactional
    public void savingOffer(CategoryRepository categoryRepository, SupplierRepository supplierRepository) {
        List<Offer> offersList = XMLParserSAX.xmlParserSAX(categoryRepository, supplierRepository);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
   //     System.out.println(offerRepository.getDateOffer() + "      "  +  LocalDate.parse(dateFormat.format(new Date())));
        if (!offerRepository.getDateOffer().equals(LocalDate.parse(dateFormat.format(new Date())))){
               for (Offer offer : offersList)
                   offerRepository.save(offer);
        }

    }



}
