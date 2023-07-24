package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dtos.customers.CustomerDto;
import exam.model.entities.Customer;
import exam.repository.CustomerRepository;
import exam.repository.TownRepository;
import exam.service.CustomerService;
import exam.util.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private TownRepository townRepository;
    private ValidationUtils validationUtils;
    private Gson gson;
    private ModelMapper modelMapper;
    private static String CUSTOMERS_FILE_PATH = "src/main/resources/files/json/customers.json";

    public CustomerServiceImpl(CustomerRepository customerRepository, TownRepository townRepository, ValidationUtils validationUtils, Gson gson, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.townRepository = townRepository;
        this.validationUtils = validationUtils;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.customerRepository.count() > 0;
    }

    @Override
    public String readCustomersFileContent() throws IOException {
        return Files.readString(Path.of(CUSTOMERS_FILE_PATH));
    }

    @Override
    public String importCustomers() throws IOException {
        StringBuilder sb = new StringBuilder();
        List<CustomerDto> customers = Arrays.stream(gson.fromJson(readCustomersFileContent(), CustomerDto[].class)).toList();

        for (CustomerDto customer : customers) {
            if(!validationUtils.isValid(customer) || this.customerRepository.findByEmail(customer.getEmail()) != null){
                sb.append("Invalid Customer").append(System.lineSeparator());
                continue;
            }
            Customer customerToSave = modelMapper.map(customer, Customer.class);
            customerToSave.setTown(townRepository.findByName(customer.getTown().getName()));
            customerToSave.setRegisteredOn(LocalDate.parse(customer.getRegisteredOn(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            this.customerRepository.saveAndFlush(customerToSave);
            sb.append(String.format("Successfully imported Customer %s %s - %s",
                            customerToSave.getFirstName(),
                            customerToSave.getLastName(),
                            customerToSave.getEmail()))
                    .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
