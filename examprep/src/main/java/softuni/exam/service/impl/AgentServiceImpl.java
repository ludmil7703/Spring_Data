package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.agents.AgentImportDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.repository.AgentRepository;
import softuni.exam.service.AgentService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Service
public class AgentServiceImpl implements AgentService {
    private AgentRepository agentRepository;
    private ValidationUtils validationUtils;
    private Gson gson;
    private ModelMapper modelMapper;
    private static String AGENT_FILE_PATH = "src/main/resources/files/json/agents.json";

     @Autowired
    public AgentServiceImpl(AgentRepository agentRepository, ValidationUtils validationUtils, Gson gson, ModelMapper modelMapper) {
        this.agentRepository = agentRepository;
        this.validationUtils = validationUtils;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {

         return this.agentRepository.count() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files.readString(Path.of(AGENT_FILE_PATH));
    }

    @Override
    public String importAgents() throws IOException {
        StringBuilder sb = new StringBuilder();
        List<AgentImportDTO> agents = Arrays.stream
                        (this.gson.fromJson(readAgentsFromFile(), AgentImportDTO[].class))
                .toList();

        for (AgentImportDTO agent : agents) {
            if(!validationUtils.isValid(agent) || this.agentRepository.findAgentByFirstName(agent.getFirstName()).isPresent()){
                sb.append("Invalid agent").append(System.lineSeparator());
                continue;
            }
            this.agentRepository.save(this.modelMapper.map(agent, Agent.class));
            sb.append(String.format("Successfully imported agent - %s %s",
                    agent.getFirstName(),
                    agent.getLastName()))
                    .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
