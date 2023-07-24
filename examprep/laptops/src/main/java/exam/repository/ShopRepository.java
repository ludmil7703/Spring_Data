package exam.repository;

import exam.model.entities.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//ToDo:
@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    Shop findByName(String name);
}
