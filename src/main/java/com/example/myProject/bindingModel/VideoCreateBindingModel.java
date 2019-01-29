package com.example.myProject.bindingModel;


import com.example.myProject.enums.MuscleGroups;

public class VideoCreateBindingModel {
    String url;

    MuscleGroups muscleGroup;

    public VideoCreateBindingModel() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MuscleGroups getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(MuscleGroups muscleGroup) {
        this.muscleGroup = muscleGroup;
    }
}
