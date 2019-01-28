package com.example.myProject.services;

import com.example.myProject.bindingModel.UserBindingModel;
import com.example.myProject.bindingModel.UserLoginBindingModel;
import com.example.myProject.entities.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(Integer id);
    User findByUsername(String username);
    User findByEmail(String email);

    void registerUser(UserBindingModel userBindingModel);
    boolean checkIfUserExists(UserLoginBindingModel userLoginBindingModel);
    boolean checkIfPasswordMatches(UserLoginBindingModel userLoginBindingModel);

}
