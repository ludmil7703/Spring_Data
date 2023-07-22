package com.example.football.service.impl;

import com.example.football.models.dto.TownDTO;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.example.football.util.ValidationUtils;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


//ToDo - Implement all methods
@Service
public class TownServiceImpl implements TownService {
    private TownRepository townRepository;
    private ValidationUtils validationUtils;
    private Gson gson;
    private ModelMapper modelMapper;
    private static String TOWNS_FILE_PATH = "src/main/resources/files/json/towns.json";
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
        return Files.readString(Path.of(TOWNS_FILE_PATH));
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder sb = new StringBuilder();
        List<TownDTO> towns = Arrays.stream(gson.fromJson(readTownsFileContent(), TownDTO[].class))
                .toList();

        for (TownDTO town : towns) {
            if(!validationUtils.isValid(town) || this.townRepository.findByName(town.getName()) != null){
                sb.append("Invalid Town").append(System.lineSeparator());
                continue;
            }

            this.townRepository.save(this.modelMapper.map(town, Town.class));
            sb.append(String.format("Successfully imported Town %s - %d",
                    town.getName(),
                    town.getPopulation()))
                    .append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
