package softuni.exam.models.dto.stars;

import softuni.exam.models.entity.StarType;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class StarDto {
    @Size(min = 6)
    private String description;
    @Positive
    private Double lightYears;
    @Size(min = 2,max = 30)
    private String name;
    private StarType starType;
    private Long constellation;
    public StarDto() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLightYears() {
        return lightYears;
    }

    public void setLightYears(Double lightYears) {
        this.lightYears = lightYears;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StarType getStarType() {
        return starType;
    }

    public void setStarType(StarType starType) {
        this.starType = starType;
    }

    public Long getConstellation() {
        return constellation;
    }

    public void setConstellation(Long constellation) {
        this.constellation = constellation;
    }
}
