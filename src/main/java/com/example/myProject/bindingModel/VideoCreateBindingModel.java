package com.example.myProject.bindingModel;


import com.example.myProject.enums.Gender;
import com.example.myProject.enums.MuscleGroups;

import javax.validation.constraints.Pattern;

public class VideoCreateBindingModel {

    @Pattern(regexp = "^(https?\\:\\/\\/)?(www\\.youtube\\.com|youtu\\.?be)\\/.+$",
            message = "Invalid video url.")
    String url;

    MuscleGroups muscleGroup;

    Gender gender;

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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
