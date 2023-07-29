package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.stars.StarDto;
import softuni.exam.models.entity.Constellation;
import softuni.exam.models.entity.Star;
import softuni.exam.models.entity.StarType;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.StarService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

// TODO: Implement all methods
@Service
public class StarServiceImpl implements StarService {
    private StarRepository starRepository;
    private ConstellationRepository constellationRepository;
    private ValidationUtils validationUtils;
    private Gson gson;
    private ModelMapper modelMapper;
    private static String CUSTOMERS_FILE_PATH = "src/main/resources/files/json/stars.json";

    @Autowired
    public StarServiceImpl(StarRepository starRepository, ConstellationRepository constellationRepository, ValidationUtils validationUtils, Gson gson, ModelMapper modelMapper) {
        this.starRepository = starRepository;
        this.constellationRepository = constellationRepository;
        this.validationUtils = validationUtils;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.starRepository.count() > 0;
    }

    @Override
    public String readStarsFileContent() throws IOException {
        return Files.readString(Path.of(CUSTOMERS_FILE_PATH));
    }

    @Override
    public String importStars() throws IOException {
        StringBuilder sb = new StringBuilder();

        StarDto[] stars = gson.fromJson(readStarsFileContent(), StarDto[].class);

        for (StarDto star : stars) {
            if (!this.validationUtils.isValid(star)
                    || this.starRepository.findByName(star.getName()) != null) {
                sb.append("Invalid star").append(System.lineSeparator());
                continue;
            }

            Star starToSave = modelMapper.map(star, Star.class);
            Constellation constellation = this.constellationRepository.findById(star.getConstellation()).orElse(null);

            starToSave.setConstellation(constellation);

            this.starRepository.save(starToSave);
            sb.append(String.format("Successfully imported star %s - %.2f light years", star.getName(), star.getLightYears()))
                    .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }

    @Override
    public String exportStars() {
        StringBuilder sb = new StringBuilder();
        List<Star> starsFind = this.starRepository.findAllByStarTypeAndAstronomersIsEmptyOrderByLightYears(StarType.RED_GIANT);
        for (Star star : starsFind) {
            sb.append(String.format("Star: %s", star.getName())).append(System.lineSeparator())
                    .append(String.format("   *Distance: %.2f light years", star.getLightYears())).append(System.lineSeparator())
                    .append(String.format("   **Description: %s", star.getDescription())).append(System.lineSeparator())
                    .append(String.format("   ***Constellation: %s", star.getConstellation().getName())).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
