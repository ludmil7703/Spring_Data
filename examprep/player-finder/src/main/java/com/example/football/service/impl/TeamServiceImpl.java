package com.example.football.service.impl;

import com.example.football.models.dto.TeamDTO;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
import com.example.football.util.ValidationUtils;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

//ToDo - Implement all methods
@Service
public class TeamServiceImpl implements TeamService {
    private TownRepository townRepository;
    private TeamRepository teamRepository;
    private ValidationUtils validationUtils;
    private Gson gson;
    private ModelMapper modelMapper;
    private static String TEAM_FILE_PATH = "src/main/resources/files/json/teams.json";

    public TeamServiceImpl(TownRepository townRepository, TeamRepository teamRepository, ValidationUtils validationUtils, Gson gson, ModelMapper modelMapper) {
        this.townRepository = townRepository;
        this.teamRepository = teamRepository;
        this.validationUtils = validationUtils;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return Files.readString(Path.of(TEAM_FILE_PATH));
    }

    @Override
    public String importTeams() throws IOException {
        StringBuilder sb = new StringBuilder();

        List<TeamDTO> teams = Arrays.stream(gson.fromJson(readTeamsFileContent(), TeamDTO[].class))
                .toList();

        for (TeamDTO team : teams) {
            if(!validationUtils.isValid(team) || this.teamRepository.findByName(team.getName()) != null){
                sb.append("Invalid Team").append(System.lineSeparator());
                continue;
            }
            Town town = townRepository.findByName(team.getTownName());
            Team teamToSave = this.modelMapper.map(team, Team.class);
            teamToSave.setTown(town);

            this.teamRepository.save(teamToSave);
            sb.append(String.format("Successfully imported Team %s - %d", team.getName(), team.getFanBase()))
                    .append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
