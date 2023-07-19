package com.example.jsonexercises.services.category;

import com.example.jsonexercises.domain.models.category.CategorySummaryModel;
import com.example.jsonexercises.domain.models.category.wrappers.CategorySummaryWrapper;
import com.example.jsonexercises.repositories.CategoryRepository;
import jakarta.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.example.jsonexercises.constants.Paths.THIRD_JSON_PATH;
import static com.example.jsonexercises.constants.Paths.THIRD_XML_PATH;
import static com.example.jsonexercises.constants.Utils.writeIntoJsonFile;
import static com.example.jsonexercises.constants.Utils.writeIntoXmlFile;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategorySummaryModel> getCategorySummary() throws IOException, JAXBException {
        final List<CategorySummaryModel> categorySummaries = this.categoryRepository.getCategorySummary();

        final CategorySummaryWrapper wrapper = new CategorySummaryWrapper(categorySummaries);

        writeIntoJsonFile(categorySummaries, THIRD_JSON_PATH);
        writeIntoXmlFile(wrapper, THIRD_XML_PATH);

        return categorySummaries;
    }
}