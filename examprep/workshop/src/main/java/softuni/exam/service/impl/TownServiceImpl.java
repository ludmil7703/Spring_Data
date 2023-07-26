package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.TownDto;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Service
public class TownServiceImpl implements TownService {

    private TownRepository townRepository;
    private ValidationUtil validationUtil;
    private Gson gson;
    private ModelMapper modelMapper;
    private static String TOWNS_FILE_PATH = "src/main/resources/files/json/towns.json";

    public TownServiceImpl(TownRepository townRepository, ValidationUtil validationUtil, Gson gson, ModelMapper modelMapper) {
        this.townRepository = townRepository;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(TOWNS_FILE_PATH));
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder sb = new StringBuilder();

        List<TownDto> towns = Arrays.stream(gson.fromJson(readTownsFileContent(), TownDto[].class)).toList();

        for (TownDto town : towns) {
            if (!validationUtil.isValid(town)) {
                sb.append("Invalid town").append(System.lineSeparator());
                continue;
            }

            this.townRepository.save(modelMapper.map(town, Town.class));
            sb.append(String.format("Successfully imported Town %s - %d", town.getName(), town.getPopulation()))
                    .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
