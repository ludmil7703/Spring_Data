package softuni.exam.instagraphlite.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dtos.posts.PostDto;
import softuni.exam.instagraphlite.models.dtos.posts.PostWrapperDto;
import softuni.exam.instagraphlite.models.entities.Post;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.PostService;
import softuni.exam.instagraphlite.util.ValidationUtils;
import softuni.exam.instagraphlite.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class PostServiceImpl implements PostService{
    private PostRepository postRepository;
    private PictureRepository pictureRepository;
    private UserRepository userRepository;
    private ValidationUtils validationUtils;
    private XmlParser xmlParser;
    private ModelMapper modelMapper;
    private static String POSTS_FILE_PATH = "src/main/resources/files/posts.xml";

    public PostServiceImpl(PostRepository postRepository, PictureRepository pictureRepository, UserRepository userRepository, ValidationUtils validationUtils, XmlParser xmlParser, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.pictureRepository = pictureRepository;
        this.userRepository = userRepository;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.postRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(POSTS_FILE_PATH));
    }

    @Override
    public String importPosts() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        List<PostDto> posts = xmlParser.fromFile(Path.of(POSTS_FILE_PATH).toFile(), PostWrapperDto.class).getPosts();

        for (PostDto post : posts) {
            if (!validationUtils.isValid(post) || pictureRepository.findByPath(post.getPicture().getPath()) == null
                    || userRepository.findByUsername(post.getUser().getUsername()) == null) {
                sb.append("Invalid post").append(System.lineSeparator());
                continue;
            }

            Post postToSave = modelMapper.map(post, Post.class);
            postToSave.setPicture(pictureRepository.findByPath(post.getPicture().getPath()));
            postToSave.setUser(userRepository.findByUsername(post.getUser().getUsername()));
            this.postRepository.saveAndFlush(postToSave);
            sb.append(String.format("Successfully imported Post, made by %s", post.getUser().getUsername())).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
