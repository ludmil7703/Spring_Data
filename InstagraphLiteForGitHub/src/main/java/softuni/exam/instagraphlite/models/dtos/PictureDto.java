package softuni.exam.instagraphlite.models.dtos;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

public class PictureDto {
    @NotNull
    private String path;
    @Range(min = 500, max = 60000)
    @NotNull
    private Double size;

    public PictureDto() {
    }

    public PictureDto(String path, Double size) {
        this.path = path;
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public PictureDto setPath(String path) {
        this.path = path;
        return this;
    }

    public Double getSize() {
        return size;
    }

    public PictureDto setSize(Double size) {
        this.size = size;
        return this;
    }
}
