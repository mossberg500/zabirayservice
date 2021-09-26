package ua.zabirayrama.zabirayservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.zabirayrama.zabirayservice.domain.LoadOffer;
import ua.zabirayrama.zabirayservice.domain.Supplier;
import ua.zabirayrama.zabirayservice.parser.LoadOfferHandler;
import ua.zabirayrama.zabirayservice.parser.XMLParserSAX;
import ua.zabirayrama.zabirayservice.repo.CategoryRepository;
import ua.zabirayrama.zabirayservice.repo.LoadCategoryRepository;
import ua.zabirayrama.zabirayservice.repo.LoadOfferRepository;
import ua.zabirayrama.zabirayservice.repo.SupplierRepository;

import java.util.ArrayList;
import java.util.List;


// всегда нужно создавать отдельный класс Service для доступа к данным, даже если кажется,
// что мало методов или это все можно реализовать сразу в контроллере
// Такой подход полезен для будущих доработок и правильной архитектуры (особенно, если работаете с транзакциями)
@Service

// все методы класса должны выполниться без ошибки, чтобы транзакция завершилась
// если в методе возникнет исключение - все выполненные операции откатятся (Rollback)
@Transactional
public class LoadOfferService {

    private final LoadOfferRepository loadOfferRepository;

    public LoadOfferService(LoadOfferRepository loadOfferRepository) {
        this.loadOfferRepository = loadOfferRepository;
    }


    @Transactional
    public void savingLoadOffer(LoadCategoryRepository loadCategoryRepository, SupplierRepository supplierRepository) {
        List<Supplier> suppliers = new ArrayList<>();
        suppliers = supplierRepository.findAll();
        for (Supplier supplier : suppliers) {
            if (supplier.getId() != 0) {
                List<LoadOffer> loadOffersList = XMLParserSAX.xmlParserSAX(loadCategoryRepository, supplierRepository, supplier.getUrlCompany(), supplier.getId().toString());
                for (LoadOffer loadOffer : loadOffersList) {
                    loadOfferRepository.save(loadOffer);
                }
            }
        }

    }


}
