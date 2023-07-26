package softuni.exam.instagraphlite.models.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity{
    @Column(nullable = false,unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @ManyToOne()
    private Picture profilePicture;
    @OneToMany(targetEntity = Post.class,mappedBy = "user")
    private List<Post> posts;

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public User(String username, String password, Picture profilePicture, List<Post> posts) {
        this.username = username;
        this.password = password;
        this.profilePicture = profilePicture;
        this.posts = posts;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;

    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;

    }

    public Picture getProfilePicture() {
        return profilePicture;
    }

    public User setProfilePicture(Picture profilePicture) {
        this.profilePicture = profilePicture;
        return this;

    }
}
