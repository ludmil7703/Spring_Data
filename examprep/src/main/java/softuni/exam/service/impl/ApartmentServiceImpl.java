package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.apartments.ApartmentImportDTO;
import softuni.exam.models.dto.apartments.ApartmentWrapperDTO;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.ApartmentService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class ApartmentServiceImpl implements ApartmentService {
    private ApartmentRepository apartmentRepository;
    private TownRepository townRepository;
    private ValidationUtils validationUtils;
    private XmlParser xmlParser;
    private ModelMapper modelMapper;
    private static String APARTMENT_FILE_PATH = "src/main/resources/files/xml/apartments.xml";

    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, TownRepository townRepository, ValidationUtils validationUtils, XmlParser xmlParser, ModelMapper modelMapper) {
        this.apartmentRepository = apartmentRepository;
        this.townRepository = townRepository;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {

        return this.apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(Path.of(APARTMENT_FILE_PATH));
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        final StringBuilder sb = new StringBuilder();

        List<ApartmentImportDTO> apartments = this.xmlParser.fromFile(Path.of(APARTMENT_FILE_PATH).toFile(), ApartmentWrapperDTO.class).getApartments();

        for (ApartmentImportDTO apartment : apartments) {
            List<Apartment> byTown = this.apartmentRepository.findAllByAreaAndTownTownName(apartment.getArea(),apartment.getTown());

            if (byTown.size() > 0 || apartment.getArea() < 40) {
                sb.append("Invalid apartment").append(System.lineSeparator());
                continue;
            }
            Apartment apartmentToSave = this.modelMapper.map(apartment, Apartment.class);
            Town byTownName = this.townRepository.findByTownName(apartment.getTown());
            apartmentToSave.setTown(byTownName);

            this.apartmentRepository.save(apartmentToSave);
            sb.append(String.format("Successfully imported apartment %s - %.2f",
                            apartment.getApartmentType(),
                            apartment.getArea()))
                    .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
