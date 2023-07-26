package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dtos.PictureDto;
import softuni.exam.instagraphlite.models.entities.Picture;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {
    private PictureRepository pictureRepository;
    private ValidationUtils validationUtils;
    private Gson gson;
    private ModelMapper modelMapper;
    private static String PICTURES_FILE_PATH = "src/main/resources/files/pictures.json";

    public PictureServiceImpl(PictureRepository pictureRepository, ValidationUtils validationUtils, Gson gson, ModelMapper modelMapper) {
        this.pictureRepository = pictureRepository;
        this.validationUtils = validationUtils;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(PICTURES_FILE_PATH));
    }

    @Override
    public String importPictures() throws IOException {
        StringBuilder sb = new StringBuilder();
        List<PictureDto> pictures = Arrays.stream(gson.fromJson(readFromFileContent(), PictureDto[].class)).toList();

        for (PictureDto picture : pictures) {
            if (!validationUtils.isValid(picture) || pictureRepository.findByPath(picture.getPath()) != null) {
                sb.append("Invalid Picture").append(System.lineSeparator());
                continue;
            }
            pictureRepository.save(modelMapper.map(picture, Picture.class));
            sb.append(String.format("Successfully imported Picture, with size %.2f", picture.getSize())).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }

    @Override
    public String exportPictures() {
        StringBuilder sb = new StringBuilder();
        List<Picture> picturesFind = this.pictureRepository.findAllBySizeGreaterThanOrderBySizeAsc(30000.00);

        for (Picture picture : picturesFind) {
            sb.append(String.format("%.2f - %s", picture.getSize(), picture.getPath())).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
