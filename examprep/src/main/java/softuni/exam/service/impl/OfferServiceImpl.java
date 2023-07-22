package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.offers.OfferImportDTO;
import softuni.exam.models.dto.offers.OfferWrapperDTO;
import softuni.exam.models.entity.ApartmentType;
import softuni.exam.models.entity.Offer;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.OfferService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {
    private AgentRepository agentRepository;
    private ApartmentRepository apartmentRepository;
    private OfferRepository offerRepository;
    private TownRepository townRepository;
    private ValidationUtils validationUtils;
    private XmlParser xmlParser;
    private ModelMapper modelMapper;
    private static String OFFERS_FILE_PATH = "src/main/resources/files/xml/offers.xml";

    public OfferServiceImpl(AgentRepository agentRepository, ApartmentRepository apartmentRepository, OfferRepository offerRepository, TownRepository townRepository, ValidationUtils validationUtils, XmlParser xmlParser, ModelMapper modelMapper) {
        this.agentRepository = agentRepository;
        this.apartmentRepository = apartmentRepository;
        this.offerRepository = offerRepository;
        this.townRepository = townRepository;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {

        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of(OFFERS_FILE_PATH));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        final StringBuilder sb = new StringBuilder();
        List<OfferImportDTO> offers = this.xmlParser.fromFile(Path.of(OFFERS_FILE_PATH).toFile(), OfferWrapperDTO.class)
                .getOffers();

        for (OfferImportDTO offer : offers) {
            if(this.agentRepository.findAgentByFirstName(offer.getAgent().getName()).isEmpty() ||
            this.apartmentRepository.findById(offer.getApartment().getId()).isEmpty()){
                sb.append("Invalid offer").append(System.lineSeparator());
                continue;
            }

            Offer offerToSave = new Offer();
            offerToSave.setPrice(offer.getPrice());
            offerToSave.setAgent(this.agentRepository.findAgentByFirstName(offer.getAgent().getName()).get());
            offerToSave.setApartment(this.apartmentRepository.findById(offer.getApartment().getId()).get());
            DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate parsedDate = LocalDate.parse(offer.getPublishedOn(), formatters);
            offerToSave.setPublishedOn(parsedDate);

            this.offerRepository.save(offerToSave);
            sb.append(String.format("Successfully imported offer %.2f",offer.getPrice())).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }

    @Override
    public String exportOffers() {
        StringBuilder sb = new StringBuilder();
        List<Offer> result = this.offerRepository.findAllByTypeOrderByAreaDescThenByPriceAsc(ApartmentType.three_rooms);
        for (Offer offer : result) {
            sb.append(String.format("Agent %s %s with offer №%d:\n" +
                    "-Apartment area: %.2f\n" +
                    "--Town: %s\n" +
                    "---Price: %.2f$",offer.getAgent().getFirstName(),
                    offer.getAgent().getLastName(),
                    offer.getId(),
                    offer.getApartment().getArea(),
                    offer.getApartment().getTown().getTownName(),
                    offer.getPrice()))
                    .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
