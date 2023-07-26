package softuni.exam.models.dtos;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class TownDto {
    @Size(min = 2)
    private String name;
    @Positive
    private Integer population;
    @Size(min = 10)
    private String guide;
    public TownDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }
}
