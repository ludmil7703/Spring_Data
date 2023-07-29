package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Constellation;

// TODO:
@Repository
public interface ConstellationRepository extends JpaRepository<Constellation, Long> {
    Constellation findByName(String name);
    Constellation findById(long id);

}
