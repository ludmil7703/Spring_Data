package softuni.exam.models.dto.constellations;


import javax.validation.constraints.Size;

public class ConstellationDto {
    @Size(min = 3, max = 20)
    private String name;
    @Size(min = 5)
    private String description;
    public ConstellationDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
