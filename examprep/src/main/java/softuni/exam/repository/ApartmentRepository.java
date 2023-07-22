package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Apartment;

import java.util.List;

// TODO:
@Repository
public interface ApartmentRepository  extends JpaRepository<Apartment, Long> {
    List<Apartment> findAllByAreaAndTownTownName(Double area, String townName);

}
