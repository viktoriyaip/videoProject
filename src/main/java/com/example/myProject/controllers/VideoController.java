package com.example.myProject.controllers;

import com.example.myProject.bindingModel.VideoCreateBindingModel;
import com.example.myProject.custom.PreAuthenticate;
import com.example.myProject.enums.Gender;
import com.example.myProject.enums.MuscleGroups;
import com.example.myProject.services.VideoService;
import com.example.myProject.viewModel.VideoViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/videos")
public class VideoController {
    VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService){
        this.videoService = videoService;
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

        List<List<VideoViewModel>> videosWomen = videoService.getVideosAsMatrix(videoService.videoByGenderAndMuscleGroupWomen(group));
        modelAndView.addObject("videosWomen", videosWomen);
        modelAndView.setViewName("videos/women/" + group);

        return modelAndView;
    }



    @GetMapping("/men/{group}")
    public ModelAndView viewVideosMen(@PathVariable(name = "group") String group, ModelAndView modelAndView){

        List<List<VideoViewModel>> videosMen = videoService.getVideosAsMatrix(videoService.videoByGenderAndMuscleGroupMen(group));
        modelAndView.addObject("videosMen", videosMen);
        modelAndView.setViewName("videos/men/" + group);

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
    public ModelAndView addVideoConfirmation(ModelAndView modelAndView, HttpSession httpSession,
                                             @Valid VideoCreateBindingModel videoCreateBindingModel){

        videoService.createVideo(videoCreateBindingModel);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    @PreAuthenticate(inRole = "ADMIN")
    public ModelAndView deleteVideoConfirm(ModelAndView modelAndView, @PathVariable(name = "id") Integer id, HttpSession session){

        this.videoService.deleteVideo(id);

        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @GetMapping("/addFavourites/{id}")
    @PreAuthenticate
    public ModelAndView addFavourites(ModelAndView modelAndView, @PathVariable(name = "id") Integer id , HttpSession session){

        String username= (String) session.getAttribute("username");
        VideoViewModel favorites = this.videoService.addToFavorites(id, username);
        modelAndView.addObject("favorites", favorites);

        modelAndView.setViewName("redirect:/user/profile");

        return modelAndView;
    }


}
