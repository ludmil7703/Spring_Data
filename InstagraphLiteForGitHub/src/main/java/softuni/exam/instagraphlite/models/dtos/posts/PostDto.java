package softuni.exam.instagraphlite.models.dtos.posts;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "post")
@XmlAccessorType(XmlAccessType.FIELD)
public class PostDto {
    @XmlElement
    @Size(min = 21)
    @NotNull
    private String caption;
    @NotNull
    @XmlElement(name = "user")
    private UserBaseDto user;
    @NotNull
    @XmlElement(name = "picture")
    private PictureBaseDto picture;

    public PostDto() {
    }

    public PostDto(String caption, UserBaseDto user, PictureBaseDto picture) {
        this.caption = caption;
        this.user = user;
        this.picture = picture;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public UserBaseDto getUser() {
        return user;
    }

    public void setUser(UserBaseDto user) {
        this.user = user;
    }

    public PictureBaseDto getPicture() {
        return picture;
    }

    public void setPicture(PictureBaseDto picture) {
        this.picture = picture;
    }
}
