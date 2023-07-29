package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.astronomers.AstronomerImportModel;
import softuni.exam.models.dto.astronomers.AstronomersWrapperImportModel;
import softuni.exam.models.entity.Astronomer;
import softuni.exam.models.entity.Star;
import softuni.exam.repository.AstronomerRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.AstronomerService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

// TODO: Implement all methods
@Service
public class AstronomerServiceImpl implements AstronomerService {
    private AstronomerRepository astronomerRepository;
    private StarRepository starRepository;
    private ValidationUtils validationUtils;
    private XmlParser xmlParser;
    private ModelMapper modelMapper;
    private static String ASTRONOMERS_FILE_PATH = "src/main/resources/files/xml/astronomers.xml";

    @Autowired
    public AstronomerServiceImpl(AstronomerRepository astronomerRepository, StarRepository starRepository, ValidationUtils validationUtils, XmlParser xmlParser, ModelMapper modelMapper) {
        this.astronomerRepository = astronomerRepository;
        this.starRepository = starRepository;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.astronomerRepository.count() > 0;
    }

    @Override
    public String readAstronomersFromFile() throws IOException {
        return Files.readString(Path.of(ASTRONOMERS_FILE_PATH));
    }

    @Override
    public String importAstronomers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        List<AstronomerImportModel> astronomers =
                xmlParser.fromFile(Path.of(ASTRONOMERS_FILE_PATH).toFile(), AstronomersWrapperImportModel.class).getAstronomers();

        for (AstronomerImportModel astronomer : astronomers) {
            Star starToObserve = this.starRepository.findById(astronomer.getStarId()).orElse(null);
            if(!this.validationUtils.isValid(astronomer) ||
                    starToObserve == null
                    || this.astronomerRepository.findAstronomerByFirstNameAndLastName(astronomer.getFirstName(), astronomer.getLastName()) != null){
                sb.append("Invalid astronomer").append(System.lineSeparator());
                continue;
            }
            Astronomer astronomerToSave = modelMapper.map(astronomer, Astronomer.class);
            astronomerToSave.setStar(starToObserve);
            astronomerToSave.setBirthDay(LocalDate.parse(astronomer.getBirthDay(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            this.astronomerRepository.saveAndFlush(astronomerToSave);
            sb.append(String.format("Successfully imported astronomer %s %s - %.2f",
                    astronomer.getFirstName(), astronomer.getLastName(), astronomer.getAverageObservationHours()))
                    .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
