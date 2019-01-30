package com.example.myProject.controllers;

import com.example.myProject.bindingModel.VideoCreateBindingModel;
import com.example.myProject.custom.PreAuthenticate;
import com.example.myProject.entities.Category;
import com.example.myProject.entities.Video;
import com.example.myProject.enums.Gender;
import com.example.myProject.enums.MuscleGroups;
import com.example.myProject.repositories.VideoRepository;
import com.example.myProject.services.CategoryService;
import com.example.myProject.services.VideoService;
import com.example.myProject.viewModel.VideoViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/videos")
public class VideoController {
    VideoService videoService;
    CategoryService categoryService;

    @Autowired
    public VideoController(VideoService videoService, CategoryService categoryService){
        this.videoService = videoService;
        this.categoryService = categoryService;
    }

    @GetMapping("/women")
    public ModelAndView womenWorkouts(ModelAndView modelAndView){

        modelAndView.setViewName("videos/women");

        return modelAndView;

    }

    @GetMapping("/men")
    public ModelAndView menWorkouts(ModelAndView modelAndView){
        modelAndView.setViewName("videos/men");

        return modelAndView;

    }

    @GetMapping("/women/{group}")
    public ModelAndView viewVideosWomen(@PathVariable(name = "group") String group, ModelAndView modelAndView){

        List<String> womenVideos = videoService.videoByGenderAndMuscleGroup(group);
        modelAndView.addObject("womenVideos",womenVideos);

        modelAndView.setViewName("videos/women/" + group);

        return modelAndView;
    }

    @GetMapping("/men/{group}")
    public ModelAndView viewVideosMen(@PathVariable(name = "group") String group, ModelAndView modelAndView){

        List<String> menVideos = videoService.videoByGenderAndMuscleGroup(group);
        modelAndView.addObject("menVideos",menVideos);

        modelAndView.setViewName("videos/men/legs");

        return modelAndView;
    }

    @GetMapping("/add")
    @PreAuthenticate(inRole = "ADMIN")
    public ModelAndView addVideo(ModelAndView modelAndView){
        modelAndView.addObject("groups", MuscleGroups.values());
        modelAndView.addObject("gender", Gender.values());
        modelAndView.addObject("videoModel",VideoCreateBindingModel.class);
        modelAndView.setViewName("videos/add");

        return modelAndView;
    }

    @PostMapping("/add")
    @PreAuthenticate(inRole = "ADMIN")
    public ModelAndView addVideoConfirmation(ModelAndView modelAndView,
                                             HttpSession httpSession,@Valid VideoCreateBindingModel videoCreateBindingModel){

        videoService.createVideo(videoCreateBindingModel);


//        videoService.createVideo(videoCreateBindingModel);
//        videoService.saveVideo(videoCreateBindingModel,httpSession.getAttribute("username").toString());
//        modelAndView.addObject("videoModel",new VideoCreateBindingModel());
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

//    @GetMapping("/women/legs")
//    public ModelAndView womenLegs(ModelAndView modelAndView){
//        modelAndView.setViewName("videos/women/legs");
//
//        return modelAndView;
//
//    }
}
