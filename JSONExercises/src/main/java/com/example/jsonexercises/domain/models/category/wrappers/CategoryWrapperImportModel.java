package com.example.jsonexercises.domain.models.category.wrappers;

import com.example.jsonexercises.domain.models.category.CategoryImportModel;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryWrapperImportModel {

    @XmlElement(name = "category")
    private List<CategoryImportModel> categories;

}