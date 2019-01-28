package com.example.myProject.entities;

import com.example.myProject.enums.MuscleGroups;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @OneToMany
    Set<Video> videos;

    @Enumerated(EnumType.STRING)
    MuscleGroups muscleGroups;


    public Category(Set<Video> videos, MuscleGroups muscleGroups) {
        this.videos = videos;
        this.muscleGroups = muscleGroups;

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

  }
