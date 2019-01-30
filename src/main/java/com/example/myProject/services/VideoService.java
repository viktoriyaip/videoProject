package com.example.myProject.services;


import com.example.myProject.bindingModel.VideoCreateBindingModel;
import com.example.myProject.entities.Video;

import java.util.List;

public interface VideoService {

    List<Video> findAll();
    Video findByUrl(String url);
    void createVideo(VideoCreateBindingModel model);
    boolean saveVideo(VideoCreateBindingModel videoCreateBindingModel, String username);
    List<Video> videoByGenderAndMuscleGroup(String group);

}
