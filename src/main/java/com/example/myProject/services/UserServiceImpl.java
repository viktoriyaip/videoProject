package com.example.myProject.services;

import com.example.myProject.bindingModel.UserBindingModel;
import com.example.myProject.bindingModel.UserLoginBindingModel;
import com.example.myProject.enums.Role;
import com.example.myProject.entities.User;
import com.example.myProject.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    UserRepository repository;
    ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository repository,ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public void registerUser(UserBindingModel userBindingModel) {
        if(!userBindingModel.getPassword().equals(userBindingModel.getConfirmPassword())){
            throw  new IllegalStateException("passwords mismatch");
        }

        User user = modelMapper.map(userBindingModel, User.class);
        user.setUsername(userBindingModel.getUsername());
        user.setPassword(userBindingModel.getPassword());
        user.setEmail(userBindingModel.getEmail());

        if(this.repository.findAll().size() == 0){
            user.setRole(Role.ADMIN);
        }else{
            user.setRole(Role.USER);
        }

        this.repository.save(user);
    }

    @Override
    public boolean checkIfUserExists(UserLoginBindingModel userLoginBindingModel) {
        return this.findByUsername(userLoginBindingModel.getUsername())!= null;
    }

    @Override
    public boolean checkIfPasswordMatches(UserLoginBindingModel userLoginBindingModel) {
        User user = repository.findByUsername(userLoginBindingModel.getUsername());
        return user.getPassword().equals(userLoginBindingModel.getPassword());
    }


}
