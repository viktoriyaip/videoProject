package com.example.myProject.services;

import com.example.myProject.bindingModel.UserBindingModel;
import com.example.myProject.bindingModel.UserLoginBindingModel;
import com.example.myProject.entities.User;
import com.example.myProject.viewModel.VideoViewModel;

import java.util.List;

public interface UserService {
    User findByUsername(String username);
    void registerUser(UserBindingModel userBindingModel);
    boolean checkIfUserExists(UserLoginBindingModel userLoginBindingModel);
    boolean checkIfPasswordMatches(UserLoginBindingModel userLoginBindingModel);
    List<VideoViewModel> addVideosToFavourites(String favId, VideoService videoService);
}
