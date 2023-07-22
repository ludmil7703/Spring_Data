package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.towns.TownImportDTO;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;


@Service
public class TownServiceImpl implements TownService {
    private TownRepository townRepository;
    private ValidationUtils validationUtils;
    private Gson gson;
    private ModelMapper modelMapper;
    private static String TOWN_FILE_PATH = "src/main/resources/files/json/towns.json";

    @Autowired
    public TownServiceImpl(TownRepository townRepository, ValidationUtils validationUtils, Gson gson, ModelMapper modelMapper) {
        this.townRepository = townRepository;
        this.validationUtils = validationUtils;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(TOWN_FILE_PATH));
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder sb = new StringBuilder();
        List<TownImportDTO> towns = Arrays.stream
                        (this.gson.fromJson(readTownsFileContent(), TownImportDTO[].class))
                .toList();

        for (TownImportDTO town : towns) {
            if (!this.validationUtils.isValid(town)) {
                sb.append("Invalid town").append(System.lineSeparator());
                continue;
            }

            this.townRepository.save(this.modelMapper.map(town, Town.class));
            sb.append(String.format("Successfully imported town %s - %d", town.getTownName(), town.getPopulation()))
                    .append(System.lineSeparator());

        }
            return sb.toString().trim();
    }
}

