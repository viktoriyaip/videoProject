package com.example.myProject.services;

import com.example.myProject.bindingModel.VideoCreateBindingModel;
import com.example.myProject.entities.Category;
import com.example.myProject.entities.User;
import com.example.myProject.entities.Video;
import com.example.myProject.enums.Gender;
import com.example.myProject.repositories.CategoryRepository;
import com.example.myProject.repositories.VideoRepository;
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
public class VideoServiceImpl implements VideoService {

    UserService userService;
    VideoRepository repository;
    CategoryRepository categoryRepository;
    ModelMapper modelMapper;



    @Autowired
    public VideoServiceImpl(UserService userService, VideoRepository repository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
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
            if(category.getMuscleGroups().equals(model.getMuscleGroup()) && category.getGender().equals(model.getGender())){
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

    @Override
    public List<String> videoByGenderAndMuscleGroupWomen(String group) {
        List<String> videoWomenUrl = new ArrayList<>();
        List<String> embedUrl = new ArrayList<>();
        List<Video> videos = repository.findAll();

        for (Video video : videos) {
            if (video.getCategory().getGender().equals(Gender.WOMEN) &&
                    video.getCategory().getMuscleGroups().getDisplayName().equals(group)) {
               videoWomenUrl.add(video.getUrl());
            }
        }

        Pattern pattern = Pattern.compile("[a-zA-Z0-9\\_\\-]+$");

        for (String s : videoWomenUrl) {
            Matcher matcher = pattern.matcher(s);
            matcher.find();
            embedUrl.add(matcher.group());
        }


        return embedUrl;
    }

    @Override
    public List<VideoViewModel> videoByGenderAndMuscleGroupMen(String group) {
        List<Video> videoWomenUrl = new ArrayList<>();
        List<Video> videos = repository.findAll();

        for (Video video : videos) {
            if (video.getCategory().getGender().equals(Gender.MEN) &&
                    video.getCategory().getMuscleGroups().getDisplayName().equals(group)) {
                videoWomenUrl.add(video);
            }
        }

        Pattern pattern = Pattern.compile("[a-zA-Z0-9\\_\\-]+$");

        for (Video s : videoWomenUrl) {
            Matcher matcher = pattern.matcher(s.getUrl());
            matcher.find();
            s.setUrl(matcher.group());
        }


        return videoWomenUrl.stream()
                .map(x-> modelMapper.map(x, VideoViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteVideo(Integer id) {
        Video video = repository.findById(id).orElse(null);
        if(video == null){
            return;
        }
        this.repository.delete(video);
    }

    @Override
    public Video findById(Integer id) {
        return this.repository.getOne(id);
    }



}
