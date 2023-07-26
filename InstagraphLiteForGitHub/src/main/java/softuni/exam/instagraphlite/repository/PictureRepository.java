package softuni.exam.instagraphlite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.instagraphlite.models.entities.Picture;

import java.util.List;

//ToDo
@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
    Picture findByPath(String path);

    List<Picture> findAllBySizeGreaterThanOrderBySizeAsc(Double size);
}
