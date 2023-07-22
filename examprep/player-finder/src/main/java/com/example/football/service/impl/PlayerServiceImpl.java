package com.example.football.service.impl;

import com.example.football.models.dto.players.PlayerImportModel;
import com.example.football.models.dto.players.PlayerWrapperImportModel;
import com.example.football.models.entity.Player;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
import com.example.football.util.ValidationUtils;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;

//ToDo - Implement all methods
@Service
public class PlayerServiceImpl implements PlayerService {
    private TownRepository townRepository;
    private StatRepository statRepository;
    private TeamRepository teamRepository;
    private PlayerRepository playerRepository;
    private ValidationUtils validationUtils;
    private XmlParser xmlParser;
    private ModelMapper modelMapper;
    private static String PLAYERS_FILE_PATH = "src/main/resources/files/xml/players.xml";

    public PlayerServiceImpl(TownRepository townRepository, StatRepository statRepository, TeamRepository teamRepository, PlayerRepository playerRepository, ValidationUtils validationUtils, XmlParser xmlParser, ModelMapper modelMapper) {
        this.townRepository = townRepository;
        this.statRepository = statRepository;
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(Path.of(PLAYERS_FILE_PATH));
    }

    @Override
    public String importPlayers() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        List<PlayerImportModel> players = xmlParser.fromFile(Path.of(PLAYERS_FILE_PATH).toFile(), PlayerWrapperImportModel.class)
                .getPlayers();

        for (PlayerImportModel player : players) {

                if(!validationUtils.isValid(player) ||
                        this.playerRepository.findByEmail(player.getEmail()) != null){
                    sb.append("Invalid Player").append(System.lineSeparator());
                    continue;
                }

            Player playerToSave = this.modelMapper.map(player, Player.class);
                playerToSave.setBirthDate(LocalDate.parse(player.getBirthDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));


            playerToSave.setStat(this.statRepository.findById(player.getStat().getId()).orElse(null));
            playerToSave.setTeam(this.teamRepository.findByName(player.getTeam().getName()));
            playerToSave.setTown(this.townRepository.findByName(player.getTown().getName()));
            this.playerRepository.saveAndFlush(playerToSave);

                sb.append(String.format("Successfully imported Player %s %s - %s",
                        player.getFirstName(), player.getLastName(), player.getPosition()))
                        .append(System.lineSeparator());
        }

        return sb.toString().trim();
    }

    @Override
    public String exportBestPlayers() {
        StringBuilder sb = new StringBuilder();
        List<Player> playersFiltered = this.playerRepository
                .findAllByBirthDateAfterAndBirthDateBefore
                        (LocalDate.parse("1995-01-01"), LocalDate.parse("2003-01-01"));
        for (Player player : playersFiltered) {
            sb.append(String.format("Player - %s %s\n" +
                            "\tPosition - %s\n" +
                            "\tTeam - %s\n" +
                            "\tStadium - %s\n",
                    player.getFirstName(), player.getLastName(), player.getPosition(),
                    player.getTeam().getName(), player.getTeam().getStadiumName()))
                    .append(System.lineSeparator());

        }
        return sb.toString().trim();
    }
}
