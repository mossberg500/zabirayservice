package ua.zabirayrama.zabirayservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.zabirayrama.zabirayservice.domain.LoadCategory;

import java.util.List;

@Repository
public interface LoadCategoryRepository extends JpaRepository<LoadCategory, Long> {

    @Override
    List<LoadCategory> findAll();

    LoadCategory save(LoadCategory loadCategory);

    @Query("select u from LoadCategory u where u.id = :id")
    LoadCategory findLoadCategoriesByid(@Param("id") Long id);



}
