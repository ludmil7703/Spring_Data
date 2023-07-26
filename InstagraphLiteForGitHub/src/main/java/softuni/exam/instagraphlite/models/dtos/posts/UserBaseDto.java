package softuni.exam.instagraphlite.models.dtos.posts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserBaseDto {
    @XmlElement
    private String username;

    public UserBaseDto() {
    }

    public UserBaseDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public UserBaseDto setUsername(String username) {
        this.username = username;
        return this;
    }
}
