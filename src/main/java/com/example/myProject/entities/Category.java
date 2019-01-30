package com.example.myProject.entities;

import com.example.myProject.enums.Gender;
import com.example.myProject.enums.MuscleGroups;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @OneToMany(mappedBy = "category")
    Set<Video> videos;

    @Enumerated(EnumType.STRING)
    MuscleGroups muscleGroups;

    @Enumerated(EnumType.STRING)
    Gender gender;

    public Category(Set<Video> videos, MuscleGroups muscleGroups, Gender gender) {
        this.videos = videos;
        this.muscleGroups = muscleGroups;
        this.gender = gender;

    }
    public Category(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Video> getVideos() {
        return videos;
    }

    public void setVideos(Set<Video> videos) {
        this.videos = videos;
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
