package com.example.myProject.services;

import com.example.myProject.bindingModel.VideoCreateBindingModel;
import com.example.myProject.entities.Category;
import com.example.myProject.enums.MuscleGroups;

public interface CategoryService {
    Category findByMuscleGroups(String muscleGroups);

}
