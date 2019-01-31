package com.example.myProject.controllers;

import com.example.myProject.bindingModel.VideoCreateBindingModel;
import com.example.myProject.custom.PreAuthenticate;
import com.example.myProject.enums.Gender;
import com.example.myProject.enums.MuscleGroups;
import com.example.myProject.services.CategoryService;
import com.example.myProject.services.VideoService;
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

        List<List<String>> videosWomen = getVideosAsMatrix(videoService.videoByGenderAndMuscleGroupWomen(group));
        modelAndView.addObject("videosWomen", videosWomen);
        modelAndView.setViewName("videos/women/" + group);

        return modelAndView;
    }

    private List<List<String>> getVideosAsMatrix(List<String> videoByGenderAndMuscleGroup) {
        List<List<String>> videos = new ArrayList<>();

        int line = videoByGenderAndMuscleGroup.size()/3;
        int column = videoByGenderAndMuscleGroup.size()%3;
        int p =0;

        for (int k = 0; k < line; k++) {
            List<String> row = new ArrayList<>();
            for (int l = 0; l < 3 ; l++) {
                row.add(videoByGenderAndMuscleGroup.get(p));
                p++;
            }
            videos.add(row);
        }
        List<String> row = new ArrayList<>();
        for (int k = column; k >0 ; k--) {
            row.add(videoByGenderAndMuscleGroup.get(k-1));
        }
            videos.add(row);

        return videos;
    }

    @GetMapping("/men/{group}")
    public ModelAndView viewVideosMen(@PathVariable(name = "group") String group, ModelAndView modelAndView){

        List<List<String>> videosMen = getVideosAsMatrix(videoService.videoByGenderAndMuscleGroupMen(group));
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

    @PostMapping("/delete")
    @PreAuthenticate(inRole = "ADMIN")
    public ModelAndView deleteVideoConfirm(ModelAndView modelAndView, Integer id){

        this.videoService.deleteVideo(id);

        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

}
