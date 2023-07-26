package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.tickets.TicketImportModel;
import softuni.exam.models.dtos.tickets.TicketWrapperImportModel;
import softuni.exam.models.entities.Passenger;
import softuni.exam.models.entities.Ticket;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.TicketRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TicketService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    private TicketRepository ticketRepository;
    private TownRepository townRepository;
    private PassengerRepository passengerRepository;
    private ValidationUtil validationUtil;
    private XmlParser xmlParser;
    private ModelMapper modelMapper;
    private static String TICKETS_FILE_PATH = "src/main/resources/files/xml/tickets.xml";

    public TicketServiceImpl(TicketRepository ticketRepository, TownRepository townRepository, PassengerRepository passengerRepository, ValidationUtil validationUtil, XmlParser xmlParser, ModelMapper modelMapper) {
        this.ticketRepository = ticketRepository;
        this.townRepository = townRepository;
        this.passengerRepository = passengerRepository;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.ticketRepository.count() > 0;
    }

    @Override
    public String readTicketsFileContent() throws IOException {
        return Files.readString(Path.of(TICKETS_FILE_PATH));
    }

    @Override
    public String importTickets() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        List<TicketImportModel> tickets = xmlParser.fromFile(Path.of(TICKETS_FILE_PATH).toFile(), TicketWrapperImportModel.class)
                .getTickets();

        for (TicketImportModel ticket : tickets) {
           if(!validationUtil.isValid(ticket)){
               sb.append("Invalid Ticket").append(System.lineSeparator());
               continue;
           }
            Town fromTown = townRepository.findByName(ticket.getFromTown().getName());
            Town toTown = townRepository.findByName(ticket.getToTown().getName());
            Passenger passenger = passengerRepository.findByEmail(ticket.getPassenger().getEmail());

            Ticket ticketToSave = modelMapper.map(ticket, Ticket.class);

            ticketToSave.setFromTown(fromTown);
            ticketToSave.setToTown(toTown);
            ticketToSave.setPassenger(passenger);
             ticketToSave.setTakeoff(LocalDateTime.parse(ticket.getTakeoff(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
             this.ticketRepository.saveAndFlush(ticketToSave);

                sb.append(String.format("Successfully imported Ticket %s - %s", ticket.getFromTown().getName(), ticket.getToTown().getName()))
                        .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
