package softuni.exam.instagraphlite.models.dtos.posts;

import softuni.exam.instagraphlite.models.dtos.posts.PostDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@XmlRootElement(name = "posts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PostWrapperDto {
    @XmlElement(name = "post")
    List<PostDto>  posts;
    public PostWrapperDto() {
    }

    public List<PostDto> getPosts() {
        return posts;
    }

    public void setPosts(List<PostDto> posts) {
        this.posts = posts;
    }
}
