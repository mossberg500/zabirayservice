package ua.zabirayrama.zabirayservice.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.zabirayrama.zabirayservice.domain.Offer;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

  //   учитываем, что параметр может быть null или пустым
    @Query("SELECT p FROM Offer p where " +
            "(:name is null or :name='' or lower(p.name) like lower(concat('%', :name,'%'))) and" +
            "(:price is null or p.price=:price)"
 //           "(:title is null or :title='' or lower(p.title) like lower(concat('%', :title,'%'))) and" +
 //           "(:completed is null or p.completed=:completed) and " +
 //           "(:priorityId is null or p.priority.id=:priorityId) and " +
 //           "(:categoryId is null or p.category.id=:categoryId)"
    )
    // искать по всем переданным параметрам (пустые параметры учитываться не будут)
    Page<Offer> findByParams(@Param("name") String name,
                             @Param("price") Double price,
  //                           @Param("priorityId") Long priorityId,
 //                           @Param("categoryId") Long categoryId,
                            Pageable pageable
    );


    @Query("SELECT p FROM Offer p where p.date = :date")
    List<Offer> selectUpDate(@Param("date") LocalDate date);

    @Query("SELECT max(p.date) FROM Offer p")
    LocalDate getDateOffer();
}
