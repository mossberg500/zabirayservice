package ua.zabirayrama.zabirayservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import ua.zabirayrama.zabirayservice.domain.Offer;
import ua.zabirayrama.zabirayservice.parser.XMLParserSAX;
import ua.zabirayrama.zabirayservice.repo.OfferRepository;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
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

    public Page findByParams(String name, Double price, PageRequest paging){
        return offerRepository.findByParams(name, price, paging);
    }

    @Transactional
    public void savingOffer() {
        List<Offer> offersList = XMLParserSAX.xmlParserSAX();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (Offer offer : offersList) {
            //      if (offer.getDate() == LocalDate.parse(dateFormat.format(new Date()))) {
            System.out.println("save -- " + offer.toString() + " !!!!! ");
            offerRepository.save(offer);
            //      }
            //      else {
            //          System.out.println("save --  missing  !!!!! ");
            //      }
        }

    }



}
