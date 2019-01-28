package com.example.myProject.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomBeans {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
