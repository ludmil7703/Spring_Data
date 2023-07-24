package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dtos.laptops.LaptopDto;
import exam.model.entities.Laptop;
import exam.repository.LaptopRepository;
import exam.repository.ShopRepository;
import exam.service.LaptopService;
import exam.util.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Service
public class LaptopServiceImpl implements LaptopService {
    private LaptopRepository laptopRepository;
    private ShopRepository shopRepository;
    private ValidationUtils validationUtils;
    private Gson gson;
    private ModelMapper modelMapper;
    private static String LAPTOPS_FILE_PATH = "src/main/resources/files/json/laptops.json";

    public LaptopServiceImpl(LaptopRepository laptopRepository, ShopRepository shopRepository, ValidationUtils validationUtils, Gson gson, ModelMapper modelMapper) {
        this.laptopRepository = laptopRepository;
        this.shopRepository = shopRepository;
        this.validationUtils = validationUtils;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.laptopRepository.count() > 0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {
        return Files.readString(Path.of(LAPTOPS_FILE_PATH));
    }

    @Override
    public String importLaptops() throws IOException {
        StringBuilder sb = new StringBuilder();

        List<LaptopDto> laptops = Arrays.stream(gson.fromJson(readLaptopsFileContent(), LaptopDto[].class)).toList();

        for (LaptopDto laptop : laptops) {
            if(!validationUtils.isValid(laptop) ||
                    !laptop.isValidWarrantyType()|| this.laptopRepository.findByMacAddress(laptop.getMacAddress()) != null){
                sb.append("Invalid Laptop").append(System.lineSeparator());
                continue;
            }
            Laptop laptopToSave = modelMapper.map(laptop, Laptop.class);
            laptopToSave.setShop(shopRepository.findByName(laptop.getShop().getName()));
            laptopRepository.save(laptopToSave);
            sb.append(String.format("Successfully added Laptop %s - %.2f - %d - %d",
                    laptopToSave.getMacAddress(),
                    laptop.getCpuSpeed(),
                            laptop.getRam(),
                            laptop.getStorage()))
                    .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }

    @Override
    public String exportBestLaptops() {
        StringBuilder sb = new StringBuilder();
        List<Laptop> laptops = this.laptopRepository.findAllByOrderByCpuSpeedDescRamDescStorageDescMacAddressAsc();

        for (Laptop laptop : laptops) {
            sb.append(String.format("Laptop - %s\n" +
                    "*Cpu speed - %.2f\n" +
                    "**Ram - %d\n" +
                    "***Storage - %d\n" +
                    "****Price - %.2f\n" +
                    "#Shop name - %s\n" +
                    "#Town - %s",
                    laptop.getMacAddress(),
                    laptop.getCpuSpeed(),
                    laptop.getRam(),
                    laptop.getStorage(),
                    laptop.getPrice(),
                    laptop.getShop().getName(),
                    laptop.getShop().getTown().getName()))
                    .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
