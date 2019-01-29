package com.example.myProject.services;

import com.example.myProject.bindingModel.VideoCreateBindingModel;
import com.example.myProject.entities.Category;
import com.example.myProject.entities.User;
import com.example.myProject.entities.Video;
import com.example.myProject.enums.MuscleGroups;
import com.example.myProject.repositories.CategoryRepository;
import com.example.myProject.repositories.VideoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    UserService userService;
    VideoRepository repository;
    CategoryRepository categoryRepository;
    ModelMapper modelMapper;

    @Autowired
    public VideoServiceImpl(UserService userService, VideoRepository repository,CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.userService = userService;
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Video> findAll() {
        return repository.findAll();
    }

    @Override
    public Video findByUrl(String url) {
        return repository.findByUrl(url);
    }

    @Override
    public void createVideo(VideoCreateBindingModel model) {
        Video video = modelMapper.map(model, Video.class);
        List<Category> videoGroup = categoryRepository.findAll();
        for (Category category : videoGroup) {
            if(category.getMuscleGroups().equals(model.getMuscleGroup())){
                video.setCategory(category);
            }
        }
        repository.save(video);
    }


    @Override
    public boolean saveVideo(VideoCreateBindingModel videoCreateBindingModel, String username) {
        User user = this.userService.findByUsername(username);
        if (user == null) {
            return false;
        }
        Video videoEntity = modelMapper.map(videoCreateBindingModel, Video.class);
        return this.repository.save(videoEntity) != null;
    }
}
