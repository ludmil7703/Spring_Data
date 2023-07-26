package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.PassengerDto;
import softuni.exam.models.entities.Passenger;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Service
public class PassengerServiceImpl implements PassengerService {
    private PassengerRepository passengerRepository;
    private TownRepository townRepository;
    private ValidationUtil validationUtil;
    private Gson gson;
    private ModelMapper modelMapper;
    private static String PASSENGERS_FILE_PATH = "src/main/resources/files/json/passengers.json";

    public PassengerServiceImpl(PassengerRepository passengerRepository, TownRepository townRepository, ValidationUtil validationUtil, Gson gson, ModelMapper modelMapper) {
        this.passengerRepository = passengerRepository;
        this.townRepository = townRepository;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.passengerRepository.count() > 0;
    }

    @Override
    public String readPassengersFileContent() throws IOException {
        return Files.readString(Path.of(PASSENGERS_FILE_PATH));
    }

    @Override
    public String importPassengers() throws IOException {
        StringBuilder sb = new StringBuilder();

        List<PassengerDto> passengerDtos = Arrays.stream(gson.fromJson(readPassengersFileContent(), PassengerDto[].class)).toList();

        for (PassengerDto passengerDto : passengerDtos) {

            if (!validationUtil.isValid(passengerDto)) {
                sb.append("Invalid passenger").append(System.lineSeparator());
                continue;
            }
            Town town = this.townRepository.findByName(passengerDto.getTown());

            Passenger passengerToSave = modelMapper.map(passengerDto, Passenger.class);
            passengerToSave.setTown(town);
            this.passengerRepository.save(passengerToSave);
            sb.append(String.format("Successfully imported Passenger %s - %s", passengerDto.getLastName(), passengerDto.getEmail()))
                    .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }

    @Override
    public String getPassengersOrderByTicketsCountDescendingThenByEmail() {
        StringBuilder sb = new StringBuilder();

        List<Passenger> passengers = this.passengerRepository.findAll();

        for (Passenger passenger : passengers) {
            sb.append(String.format("Passenger %s  %s", passenger.getFirstName(), passenger.getLastName()))
                    .append(System.lineSeparator())
                    .append(String.format("\tEmail - %s", passenger.getEmail()))
                    .append(System.lineSeparator())
                    .append(String.format("\tPhone - %s", passenger.getPhoneNumber()))
                    .append(System.lineSeparator())
                    .append(String.format("\tNumber of tickets - %d", passenger.getTickets().size()))
                    .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
