package com.example.football.models.dto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class TownDTO {
    @Size(min = 2)
    private String name;
    @Positive
    private Integer population;
    @Size(min = 10)
    private String travelGuide;

    public TownDTO() {
    }

    public TownDTO(String name, Integer population, String travelGuide) {
        this.name = name;
        this.population = population;
        this.travelGuide = travelGuide;
    }

    public String getName() {
        return name;
    }

    public TownDTO setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getPopulation() {
        return population;
    }

    public TownDTO setPopulation(Integer population) {
        this.population = population;
        return this;
    }

    public String getTravelGuide() {
        return travelGuide;
    }

    public TownDTO setTravelGuide(String travelGuide) {
        this.travelGuide = travelGuide;
        return this;
    }
}
