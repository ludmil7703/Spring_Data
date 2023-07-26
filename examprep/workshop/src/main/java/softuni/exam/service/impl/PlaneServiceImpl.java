package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.planes.PlaneImportModel;
import softuni.exam.models.dtos.planes.PlaneWrapperImportModel;
import softuni.exam.models.entities.Plane;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.service.PlaneService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class PlaneServiceImpl implements PlaneService {
    private PlaneRepository planeRepository;
    private ValidationUtil validationUtil;
    private XmlParser xmlParser;
    private ModelMapper modelMapper;
    private static String PLANES_FILE_PATH = "src/main/resources/files/xml/planes.xml";

    public PlaneServiceImpl(PlaneRepository planeRepository, ValidationUtil validationUtil, XmlParser xmlParser, ModelMapper modelMapper) {
        this.planeRepository = planeRepository;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.planeRepository.count() > 0;
    }

    @Override
    public String readPlanesFileContent() throws IOException {
        return Files.readString(Path.of(PLANES_FILE_PATH));
    }

    @Override
    public String importPlanes() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        List<PlaneImportModel> planes = xmlParser.fromFile(Path.of(PLANES_FILE_PATH).toFile(), PlaneWrapperImportModel.class)
                .getPlanes();

        for (PlaneImportModel plane : planes) {
            if(!validationUtil.isValid(plane)){
                sb.append("Invalid Plane").append(System.lineSeparator());
                continue;
            }
            Plane planeToSave = modelMapper.map(plane, Plane.class);
            planeRepository.save(planeToSave);
            sb.append(String.format("Successfully imported Plane %s", plane.getRegisterNumber()))
                    .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
