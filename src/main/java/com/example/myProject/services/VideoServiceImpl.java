package com.example.myProject.services;

import com.example.myProject.bindingModel.VideoCreateBindingModel;
import com.example.myProject.entities.Category;
import com.example.myProject.entities.User;
import com.example.myProject.entities.Video;
import com.example.myProject.enums.Gender;
import com.example.myProject.repositories.CategoryRepository;
import com.example.myProject.repositories.UserRepository;
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
    UserRepository userRepository;



    @Autowired
    public VideoServiceImpl(UserService userService, VideoRepository repository,
                            CategoryRepository categoryRepository,
                            ModelMapper modelMapper, UserRepository userRepository) {
        this.userService = userService;
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;

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
    public List<VideoViewModel> videoByGenderAndMuscleGroupWomen(String group) {
        List<Video> videoWomenUrl = new ArrayList<>();
        List<Video> videos = repository.findAll();

        for (Video video : videos) {
            if (video.getCategory().getGender().equals(Gender.WOMEN) &&
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
                .map(x->modelMapper.map(x,VideoViewModel.class))
                .collect(Collectors.toList());
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

    @Override
    public List<List<VideoViewModel>> getVideosAsMatrix(List<VideoViewModel> videoByGenderAndMuscleGroup) {
        List<List<VideoViewModel>> videos = new ArrayList<>();

        int line = videoByGenderAndMuscleGroup.size()/3;
        int column = videoByGenderAndMuscleGroup.size()%3;
        int p =0;

        for (int k = 0; k < line; k++) {
            List<VideoViewModel> row = new ArrayList<>();
            for (int l = 0; l < 3 ; l++) {
                row.add(videoByGenderAndMuscleGroup.get(p));
                p++;
            }
            videos.add(row);
        }
        List<VideoViewModel> row = new ArrayList<>();
        for (int k = column; k >0 ; k--) {
            row.add(videoByGenderAndMuscleGroup.get(k-1));
        }
        videos.add(row);

        return videos;
    }

    @Override
    public VideoViewModel addToFavorites(Integer id,String username) {
        User user =this.userService.findByUsername(username);
        Video favVideos = null;
        List<Video> vid = repository.findAll();

        for (Video video : vid) {
            if(video.getId().equals(this.repository.getOne(id).getId())){
                Pattern pattern = Pattern.compile("[a-zA-Z0-9\\_\\-]+$");
                Matcher matcher = pattern.matcher(video.getUrl());
                matcher.find();
                video.setUrl(matcher.group());
                favVideos = video;
            }
        }
        String temp = user.getFavourites();
        temp = temp + id + "/";
        user.setFavourites(temp);
        userRepository.save(user);

        return modelMapper.map(favVideos, VideoViewModel.class);
    }

}
