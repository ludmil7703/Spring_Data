package bg.softuni.springrepositories.repositories;

import bg.softuni.springrepositories.entities.Shampoo;
import bg.softuni.springrepositories.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {
    List<Shampoo> findByBrand(String brand);

     List<Shampoo> findByBrandAndSize(String brand, Size size);

    List<Shampoo> findBySizeOrderById(Size size);


    List<Shampoo> findBySizeOrLabelIdOrderByPrice(Size size, long labelId);

    List<Shampoo> findByPriceGreaterThanOrderByPriceDesc(BigDecimal price);


    List<Shampoo> findAllByPriceLessThan(BigDecimal price);


    @Query(value = "SELECT s FROM Shampoo s JOIN s.ingredients i WHERE i.name IN :list")
    List<Shampoo> findAllWithIngredients(List<String> list);
}
