package softuni.exam.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "towns")
public class Town extends BaseEntity{
    @Column(unique = true)
    private String name;
    @Column
    private Integer population;
    @Column(columnDefinition = "TEXT")
    private String guide;

    public Town() {
    }

    public Town(String name, Integer population, String guide) {
        this.name = name;
        this.population = population;
        this.guide = guide;
    }

    public String getName() {
        return name;
    }

    public Town setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getPopulation() {
        return population;
    }

    public Town setPopulation(Integer population) {
        this.population = population;
        return this;
    }

    public String getGuide() {
        return guide;
    }

    public Town setGuide(String guide) {
        this.guide = guide;
        return this;
    }
}
