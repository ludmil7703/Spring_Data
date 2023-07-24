package exam.service.impl;

import exam.model.dtos.shops.ShopImportModel;
import exam.model.dtos.shops.ShopWrapperImportModel;
import exam.model.entities.Shop;
import exam.repository.ShopRepository;
import exam.repository.TownRepository;
import exam.service.ShopService;
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
public class ShopServiceImpl implements ShopService {
    private ShopRepository shopRepository;
    private TownRepository townRepository;
    private ValidationUtils validationUtils;
    private XmlParser xmlParser;
    private ModelMapper modelMapper;
    private static String SHOPS_FILE_PATH = "src/main/resources/files/xml/shops.xml";

    public ShopServiceImpl(ShopRepository shopRepository, TownRepository townRepository, ValidationUtils validationUtils, XmlParser xmlParser, ModelMapper modelMapper) {
        this.shopRepository = shopRepository;
        this.townRepository = townRepository;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.shopRepository.count() > 0;
    }

    @Override
    public String readShopsFileContent() throws IOException {
        return Files.readString(Path.of(SHOPS_FILE_PATH));
    }

    @Override
    public String importShops() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        List<ShopImportModel> shops = xmlParser.fromFile(Path.of(SHOPS_FILE_PATH).toFile(), ShopWrapperImportModel.class).getShops();

        for (ShopImportModel shop : shops) {
            if(!validationUtils.isValid(shop) || this.shopRepository.findByName(shop.getName()) != null){
                sb.append("Invalid shop").append(System.lineSeparator());
                continue;
            }
            Shop shopToSave = modelMapper.map(shop, Shop.class);
            shopToSave.setTown(townRepository.findByName(shop.getTown().getName()));
            shopRepository.save(shopToSave);
            sb.append(String.format("Successfully imported Shop %s - %d", shop.getName(), shop.getIncome()))
                    .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
