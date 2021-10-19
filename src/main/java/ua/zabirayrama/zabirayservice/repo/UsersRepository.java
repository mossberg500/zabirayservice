package ua.zabirayrama.zabirayservice.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.zabirayrama.zabirayservice.domain.Users;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    @Query("SELECT s FROM Users s")
    List<Users> findAll();

    @Query("SELECT s FROM Users s")
    Page<Users> findByPage(Pageable pageable);



    @Query("select u from Users u where u.id = :id")
    Optional<Users> findById(@Param("id") Long id);
}
