package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import softuni.exam.models.entity.Star;
import softuni.exam.models.entity.StarType;

import java.util.List;

// TODO:
public interface StarRepository extends JpaRepository<Star, Long> {
    Star findByName(String name);

@Query("SELECT s FROM Star s WHERE s.constellation.name = :constellationName ORDER BY s.name")
    List<Star> findAllByStarTypeAndAstronomersIsEmptyOrderByLightYears(@Param("constellationName") String constellationName);

}
