package ua.zabirayrama.zabirayservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.zabirayrama.zabirayservice.domain.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAll();



    Category save(Category category);

    //    @Query("select u.name from Offers u inner join Category c on c.id = u.category where c.id = :id")
    @Query("select u from Category u where u.id = :id")
    Category findCategoriesByid(@Param("id") Long id);


    @Query("select o.id from Offer o inner join Category u on u.id = o.category.id where u.id = :id")
    List<Long> findOffersByCategories(@Param("id") Long id);


    //   @Query("select o.id from Zabiray o inner join Category u on u.id = o.category.id where u.id = :id")
    //   List<Long> findZabiraysByCategories(@Param("id") Long id);

    //   @Query("select o.id from Yatextile o inner join Category u on u.id = o.category.id where u.id = :id")
    //   List<Long> findYatextileByCategories(@Param("id") Long id);


}
