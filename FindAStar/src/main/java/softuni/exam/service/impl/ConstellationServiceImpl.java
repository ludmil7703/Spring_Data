package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.constellations.ConstellationDto;
import softuni.exam.models.entity.Constellation;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.service.ConstellationService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

// TODO: Implement all methods
@Service
public class ConstellationServiceImpl implements ConstellationService {
    private ConstellationRepository constellationRepository;
    private ValidationUtils validationUtils;
    private Gson gson;
    private ModelMapper modelMapper;
    private static String CONSTELLATIONS_FILE_PATH = "src/main/resources/files/json/constellations.json";

    @Autowired
    public ConstellationServiceImpl(ConstellationRepository constellationRepository, ValidationUtils validationUtils, Gson gson, ModelMapper modelMapper) {
        this.constellationRepository = constellationRepository;
        this.validationUtils = validationUtils;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.constellationRepository.count() > 0;
    }

    @Override
    public String readConstellationsFromFile() throws IOException {
        return Files.readString(Path.of(CONSTELLATIONS_FILE_PATH));
    }

    @Override
    public String importConstellations() throws IOException {
        StringBuilder sb = new StringBuilder();

        ConstellationDto[] constellations = gson.fromJson(readConstellationsFromFile(), ConstellationDto[].class);

        for (ConstellationDto constellation : constellations) {
            if (!this.validationUtils.isValid(constellation)
                    || this.constellationRepository.findByName(constellation.getName()) != null) {
                sb.append("Invalid constellation").append(System.lineSeparator());
                continue;
            }
            this.constellationRepository.saveAndFlush(modelMapper.map(constellation, Constellation.class));
            sb.append(String.format("Successfully imported constellation %s - %s", constellation.getName(), constellation.getDescription()))
                    .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
