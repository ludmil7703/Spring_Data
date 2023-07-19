package com.example.jsonexercises.services.product;

import com.example.jsonexercises.domain.models.product.ProductBasicInfoWithSellerFullName;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    List<ProductBasicInfoWithSellerFullName> getProductsInRangeWithNoBuyer(BigDecimal lowBoundary, BigDecimal highBoundary) throws IOException, JAXBException;

}