package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dtos.towns.TownImportModel;
import exam.model.dtos.towns.TownWrapperImportModel;
import exam.model.entities.Town;
import exam.repository.TownRepository;
import exam.service.TownService;
import exam.util.ValidationUtils;
import exam.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class TownServiceImpl implements TownService {
    private TownRepository townRepository;
    private ValidationUtils validationUtils;
    private XmlParser xmlParser;
    private ModelMapper modelMapper;
    private static String TOWN_FILE_PATH = "src/main/resources/files/xml/towns.xml";

    public TownServiceImpl(TownRepository townRepository, ValidationUtils validationUtils, XmlParser xmlParser, ModelMapper modelMapper) {
        this.townRepository = townRepository;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
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
    public String importTowns() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        List<TownImportModel> towns = xmlParser
                .fromFile(Path.of(TOWN_FILE_PATH).toFile(), TownWrapperImportModel.class).getTowns();

        for (TownImportModel town : towns) {
            if(!validationUtils.isValid(town) || townRepository.findByName(town.getName()) != null){
                sb.append("Invalid town").append(System.lineSeparator());
                continue;
            }
            townRepository.save(modelMapper.map(town, Town.class));
            sb.append(String.format("Successfully imported Town %s", town.getName()))
                    .append(System.lineSeparator());

        }
        return sb.toString().trim();
    }
}
