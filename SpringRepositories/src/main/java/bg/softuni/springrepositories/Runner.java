package bg.softuni.springrepositories;

import bg.softuni.springrepositories.entities.Ingredient;
import bg.softuni.springrepositories.entities.Shampoo;
import bg.softuni.springrepositories.services.IngredientsService;
import bg.softuni.springrepositories.services.ShampooService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class Runner implements CommandLineRunner {
    private ShampooService shampooService;
    private IngredientsService ingredientsService;

    public Runner(ShampooService shampooService, IngredientsService ingredientsService) {
        this.shampooService = shampooService;
        this.ingredientsService = ingredientsService;
    }


    @Override
    public void run(String... args) throws Exception {
//        List<Shampoo> byBrand = shampooService.findByBrand("Swiss Green Apple & Nettle");
//        byBrand.forEach(System.out::println);

//        List<Shampoo> byBrandAndSize = shampooService.findByBrandAndSize("Swiss Green Apple & Nettle", Size.SMALL);
//
//        byBrandAndSize.forEach(System.out::println);

//        List<Shampoo> bySize = shampooService.findBySizeOrderById(Size.MEDIUM);
//        bySize.sort(Comparator.comparing(BaseEntity::getId));

//

//        List<Shampoo> result = shampooService.findByPriceGreaterThanOrderByPriceDesc(BigDecimal.valueOf(5.00));
//        List<Ingredient> result = ingredientsService.findByNameStartingWith("M");

//        List<Ingredient> result = ingredientsService.findByNameInOrderByPrice(List.of("Lavender", "Herbs", "Apple"));
//        Integer result = shampooService.findAllByPriceLessThan(BigDecimal.valueOf(8.50)).size();
//
//        System.out.println(result);

//        List<Shampoo> result = shampooService.findAllWithIngredients(List.of("Berry", "Mineral-Collagen"));
//        result.forEach(System.out::println);
//        ingredientsService.increasePriceBy10Percent();
//
//        ingredientsService.deleteByName("Apple");
        ingredientsService.updatePriceByName("Macadamia Oil", BigDecimal.valueOf(1.5));

    }
}
