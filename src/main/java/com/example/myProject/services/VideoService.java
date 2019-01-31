package com.example.myProject.services;


import com.example.myProject.bindingModel.VideoCreateBindingModel;
import com.example.myProject.entities.Video;
import com.example.myProject.viewModel.VideoViewModel;

import java.util.List;

public interface VideoService {

    List<Video> findAll();
    Video findByUrl(String url);
    void createVideo(VideoCreateBindingModel model);
    boolean saveVideo(VideoCreateBindingModel videoCreateBindingModel, String username);
    List<String> videoByGenderAndMuscleGroupWomen(String group);
    List<VideoViewModel> videoByGenderAndMuscleGroupMen(String group);
    public void deleteVideo(Integer id);

    Video findById(Integer id);

}
