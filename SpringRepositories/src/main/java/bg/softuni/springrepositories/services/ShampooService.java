package bg.softuni.springrepositories.services;


import bg.softuni.springrepositories.entities.Shampoo;
import bg.softuni.springrepositories.entities.Size;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public interface ShampooService {

    List<Shampoo> findByBrand(String brand);
    List<Shampoo> findByBrandAndSize(String brand, Size size);

    List<Shampoo> findBySizeOrderById(Size size);


    List<Shampoo> findBySizeOrLabelIdOrderByPrice(Size size, long labelId);

    List<Shampoo> findByPriceGreaterThanOrderByPriceDesc(BigDecimal price);


    List<Shampoo> findAllByPriceLessThan(BigDecimal price);


    List<Shampoo> findAllWithIngredients(List<String> list);
}
