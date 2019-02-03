package com.example.myProject.services;


import com.example.myProject.bindingModel.VideoCreateBindingModel;
import com.example.myProject.entities.Video;
import com.example.myProject.viewModel.VideoViewModel;

import java.util.List;

public interface VideoService {

    void createVideo(VideoCreateBindingModel model);
    List<VideoViewModel> videoByGenderAndMuscleGroupWomen(String group);
    List<VideoViewModel> videoByGenderAndMuscleGroupMen(String group);
    void deleteVideo(Integer id);
    List<List<VideoViewModel>> getVideosAsMatrix(List<VideoViewModel> videoByGenderAndMuscleGroup);
    Video findById(Integer id);
    VideoViewModel addToFavorites(Integer id,String username);
}
