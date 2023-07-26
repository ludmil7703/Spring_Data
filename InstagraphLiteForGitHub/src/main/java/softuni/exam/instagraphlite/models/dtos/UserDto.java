package softuni.exam.instagraphlite.models.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDto {
    @Size(min = 2, max = 18)
    @NotNull
    private String username;
    @Size(min = 4)
    @NotNull
    private String password;
    @NotNull
    private String profilePicture;

    public UserDto() {
    }

    public UserDto(String username, String password, String profilePicture) {
        this.username = username;
        this.password = password;
        this.profilePicture = profilePicture;
    }

    public String getUsername() {
        return username;
    }

    public UserDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public UserDto setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }
}
