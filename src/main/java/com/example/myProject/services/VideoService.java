package com.example.myProject.services;


import com.example.myProject.bindingModel.VideoCreateBindingModel;
import com.example.myProject.entities.Category;
import com.example.myProject.entities.Video;
import com.example.myProject.enums.MuscleGroups;

import java.util.List;

public interface VideoService {

    List<Video> findAll();
    Video findByUrl(String url);
    Video findByCategory(Category category);

    public boolean saveVideo(VideoCreateBindingModel videoCreateBindingModel, String username);
}
