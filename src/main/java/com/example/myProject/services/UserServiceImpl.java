package com.example.myProject.services;

import com.example.myProject.bindingModel.UserBindingModel;
import com.example.myProject.bindingModel.UserLoginBindingModel;
import com.example.myProject.entities.Video;
import com.example.myProject.enums.Role;
import com.example.myProject.entities.User;
import com.example.myProject.repositories.UserRepository;
import com.example.myProject.viewModel.VideoViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    public User findByUsername(String username) {
        return repository.findByUsername(username);
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
        user.setFavourites("");

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

    @Override
    public List<VideoViewModel> addVideosToFavourites(String favIds, VideoService videoService) {
        String[] ids = favIds.split("/");
        List<Video> videos = new ArrayList<>();


        for (String id : ids) {
            Integer x = Integer.valueOf(id);
            videos.add(videoService.findById(x));
        }


        for (Video video : videos) {
            Pattern pattern = Pattern.compile("[a-zA-Z0-9\\_\\-]+$");
            Matcher matcher = pattern.matcher(video.getUrl());
            matcher.find();
            video.setUrl(matcher.group());
        }

        List<VideoViewModel> videoViewModels = videos.stream()
                .map(x -> modelMapper.map(x, VideoViewModel.class))
                .collect(Collectors.toList());

        return  videoViewModels;

    }
}
