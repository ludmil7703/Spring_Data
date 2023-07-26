package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dtos.UserDto;
import softuni.exam.instagraphlite.models.entities.Picture;
import softuni.exam.instagraphlite.models.entities.Post;
import softuni.exam.instagraphlite.models.entities.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.UserService;
import softuni.exam.instagraphlite.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PostRepository postRepository;
    private PictureRepository pictureRepository;
    private ValidationUtils validationUtils;
    private Gson gson;
    private ModelMapper modelMapper;
    private static String PICTURES_FILE_PATH = "src/main/resources/files/users.json";

    public UserServiceImpl(UserRepository userRepository, PostRepository postRepository, PictureRepository pictureRepository, ValidationUtils validationUtils, Gson gson, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.pictureRepository = pictureRepository;
        this.validationUtils = validationUtils;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.userRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(PICTURES_FILE_PATH));
    }

    @Override
    public String importUsers() throws IOException {
        StringBuilder sb = new StringBuilder();
        List<UserDto> users = Arrays.stream(gson.fromJson(readFromFileContent(), UserDto[].class)).toList();

        for (UserDto user : users) {
            Picture picture = pictureRepository.findByPath(user.getProfilePicture());
            if (!validationUtils.isValid(user) || picture == null) {
                sb.append("Invalid User").append(System.lineSeparator());
                continue;
            }
            User userToSave = modelMapper.map(user, User.class).setProfilePicture(picture);
            userRepository.save(userToSave);
            sb.append(String.format("Successfully imported User: %s", user.getUsername())).append(System.lineSeparator());

        }
        return sb.toString().trim();
    }

    @Override
    public String exportUsersWithTheirPosts() {
         StringBuilder sb = new StringBuilder();
            List<User> users = userRepository.findAllByPostsOrderByPostsPostsDescThenByUserId();
            for (User user : users) {
                sb.append(String.format("User: %s", user.getUsername())).append(System.lineSeparator());
                sb.append(String.format("Post count: %d", user.getPosts().size())).append(System.lineSeparator());
                for (Post post : user.getPosts()) {
                    sb.append(String.format("==Post Details:%n" +
                            "----caption = %s%n" +
                            "----picture = %.2f%n", post.getCaption(), post.getPicture().getSize()));
                }
            }
        return sb.toString().trim();
    }
}
