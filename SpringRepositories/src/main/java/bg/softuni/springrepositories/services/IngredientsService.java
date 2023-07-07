package bg.softuni.springrepositories.services;

import bg.softuni.springrepositories.entities.Ingredient;

import java.math.BigDecimal;
import java.util.List;

public interface IngredientsService {
    List<Ingredient> findByNameStartingWith(String start);

    List<Ingredient> findByNameInThanOrderByPrice(List<String> listNames);


    void increasePriceBy10Percent();

    void deleteByName(String ingredientName);

    void updatePriceByName(String ingredientName, BigDecimal price);
}
