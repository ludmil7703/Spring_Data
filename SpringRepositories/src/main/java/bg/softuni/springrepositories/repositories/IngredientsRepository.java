package bg.softuni.springrepositories.repositories;

import bg.softuni.springrepositories.entities.Ingredient;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface IngredientsRepository extends JpaRepository<Ingredient, Long>{
    List<Ingredient> findByNameStartingWith(String start);


    List<Ingredient> findByNameInOrderByPrice(List<String> listNames);
@Modifying
@Transactional
@Query("UPDATE Ingredient SET price = price * :multiplier")
    void increasePriceBy10Percent(@Param(value = "multiplier") BigDecimal multiplier);

@Modifying
@Transactional
@Query("DELETE FROM Ingredient WHERE name = :ingredientName")
    void deleteByName(@Param(value = "ingredientName") String ingredientName);

@Modifying
@Transactional
@Query("UPDATE Ingredient SET price = :price WHERE name = :ingredientName")
    void updatePriceByName(String ingredientName, BigDecimal price);
}
