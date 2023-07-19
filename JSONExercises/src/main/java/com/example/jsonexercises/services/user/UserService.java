package com.example.jsonexercises.services.user;

import com.example.jsonexercises.domain.models.user.UserWithSoldProductsModel;
import com.example.jsonexercises.domain.models.user.wrappers.UsersWithSoldProductsWrapperModel;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.util.List;

public interface UserService {

    UsersWithSoldProductsWrapperModel getUsersAndSoldProductsWrapper() throws IOException, JAXBException;

    List<UserWithSoldProductsModel> getUsersAndSoldProducts() throws IOException, JAXBException;

}