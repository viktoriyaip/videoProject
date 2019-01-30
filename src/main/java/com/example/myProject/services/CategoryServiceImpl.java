package com.example.myProject.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(ModelMapper modelMapper) {

        this.modelMapper = modelMapper;
    }

}
