package com.example.myProject.services;

import com.example.myProject.bindingModel.VideoCreateBindingModel;
import com.example.myProject.entities.Category;
import com.example.myProject.enums.MuscleGroups;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryServiceImpl implements CategoryService {
    CategoryService categoryService;
    ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Category findByMuscleGroups(String muscleGroups) {
        return categoryService.findByMuscleGroups(muscleGroups);
    }
}
