package ua.zabirayrama.zabirayservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.zabirayrama.zabirayservice.domain.Category;
import ua.zabirayrama.zabirayservice.domain.Supplier;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {


    @Query("select u from Supplier u where u.id = :id")
    Supplier findSupplierByid(@Param("id") Long id);


    @Query("SELECT s FROM Supplier s where " +
            "(:company is null or :company='' or lower(s.company) like lower(concat('%', :company,'%')))  " +
            "order by s.company asc")
    List<Supplier> findByTitle(@Param("company") String company);

    List<Supplier> findAll();
}
