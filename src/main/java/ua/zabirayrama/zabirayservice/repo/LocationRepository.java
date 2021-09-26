package ua.zabirayrama.zabirayservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.zabirayrama.zabirayservice.domain.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

}
