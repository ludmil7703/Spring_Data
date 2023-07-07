package bg.softuni.springrepositories.services;

import bg.softuni.springrepositories.entities.Ingredient;
import bg.softuni.springrepositories.repositories.IngredientsRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class IngredientsServiceImpl implements IngredientsService{
    private final IngredientsRepository ingredientsRepository;

    public IngredientsServiceImpl(IngredientsRepository ingredientsRepository) {
        this.ingredientsRepository = ingredientsRepository;
    }

    @Override
    public List<Ingredient> findByNameStartingWith(String start) {
        return ingredientsRepository.findByNameStartingWith(start);
    }

    @Override
    public List<Ingredient> findByNameInThanOrderByPrice(List<String> listNames) {
        return ingredientsRepository.findByNameInOrderByPrice(listNames);
    }

    @Override
    public void increasePriceBy10Percent() {
        ingredientsRepository.increasePriceBy10Percent(BigDecimal.valueOf(1.1));
    }

    @Override
    public void deleteByName(String ingredientName) {
        ingredientsRepository.deleteByName(ingredientName);
    }

    @Override
    public void updatePriceByName(String ingredientName, BigDecimal price) {
        ingredientsRepository.updatePriceByName(ingredientName, price);
    }


}
