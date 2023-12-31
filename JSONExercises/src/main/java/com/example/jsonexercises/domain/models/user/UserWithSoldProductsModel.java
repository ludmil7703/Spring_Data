package com.example.jsonexercises.domain.models.user;

import com.example.jsonexercises.domain.models.product.ProductSoldModel;
import com.example.jsonexercises.domain.models.product.wrappers.ProductsSoldWrapper;
import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserWithSoldProductsModel {

    @XmlAttribute(name = "first-name")
    private String firstName;

    @XmlAttribute(name = "last-name")
    private String lastName;

    private Set<ProductSoldModel> boughtProducts;

}