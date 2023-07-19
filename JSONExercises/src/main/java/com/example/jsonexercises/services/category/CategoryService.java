package com.example.jsonexercises.services.category;

import com.example.jsonexercises.domain.models.category.CategorySummaryModel;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.util.List;

public interface CategoryService {

    List<CategorySummaryModel> getCategorySummary() throws IOException, JAXBException;

}