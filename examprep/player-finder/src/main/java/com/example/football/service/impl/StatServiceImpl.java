package com.example.football.service.impl;

import com.example.football.models.dto.stats.StatImportModel;
import com.example.football.models.dto.stats.StatWrapperImportModel;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import com.example.football.util.ValidationUtils;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

//ToDo - Implement all methods
@Service
public class StatServiceImpl implements StatService {
    private StatRepository statRepository;
    private ValidationUtils validationUtils;
    private XmlParser xmlParser;
    private ModelMapper modelMapper;
    private static String STATS_FILE_PATH = "src/main/resources/files/xml/stats.xml";

    public StatServiceImpl(StatRepository statRepository, ValidationUtils validationUtils, XmlParser xmlParser, ModelMapper modelMapper) {
        this.statRepository = statRepository;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean areImported() {
        return this.statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(Path.of(STATS_FILE_PATH));
    }

    @Override
    public String importStats() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        List<StatImportModel> stats = xmlParser.fromFile(Path.of(STATS_FILE_PATH).toFile(), StatWrapperImportModel.class)
                .getStats();
        for (StatImportModel stat : stats) {

            if(!validationUtils.isValid(stat) ||
                    this.statRepository.findByPassingAndShootingAndEndurance(stat.getPassing(), stat.getShooting(), stat.getEndurance()) != null){
                sb.append("Invalid Stat").append(System.lineSeparator());
                continue;
            }
            if(this.statRepository.count() == 0){
                stat.setId(1L);
            }
            this.statRepository.save(modelMapper.map(stat, Stat.class));
            sb.append(String.format("Successfully imported Stat %.2f - %.2f - %.2f",
                    stat.getShooting(), stat.getPassing(), stat.getEndurance()))
                    .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
