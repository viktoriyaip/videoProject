package com.example.myProject.services;

import com.example.myProject.bindingModel.UserBindingModel;
import com.example.myProject.bindingModel.UserLoginBindingModel;
import com.example.myProject.entities.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findByUsername(String username);
    void registerUser(UserBindingModel userBindingModel);
    boolean checkIfUserExists(UserLoginBindingModel userLoginBindingModel);
    boolean checkIfPasswordMatches(UserLoginBindingModel userLoginBindingModel);
    Integer getUserId(UserLoginBindingModel user);
}
