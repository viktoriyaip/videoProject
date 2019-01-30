package com.example.myProject.viewModel;

import com.example.myProject.enums.Gender;
import com.example.myProject.enums.MuscleGroups;

public class VideoViewModel {

    String url;

    MuscleGroups muscleGroups;

    Gender gender;

    public VideoViewModel() {
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
