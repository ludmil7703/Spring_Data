package softuni.exam.instagraphlite.models.dtos.posts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "picture")
@XmlAccessorType(XmlAccessType.FIELD)
public class PictureBaseDto {
    @XmlElement
    private String path;

    public PictureBaseDto() {
    }

    public PictureBaseDto(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public PictureBaseDto setPath(String path) {
        this.path = path;
        return this;
    }
}
