package com.example.myProject.bindingModel;

import com.example.myProject.enums.MuscleGroups;

public class VideoCreateBindingModel {
    String url;
    MuscleGroups muscleGroups;
    String sex;

    public VideoCreateBindingModel() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MuscleGroups getMuscleGroups() {
        return muscleGroups;
    }

    public void setMuscleGroups(MuscleGroups muscleGroups) {
        this.muscleGroups = muscleGroups;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
