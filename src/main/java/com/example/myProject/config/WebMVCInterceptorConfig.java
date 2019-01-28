package com.example.myProject.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebMVCInterceptorConfig implements WebMvcConfigurer {

    private final PreAuthenticateInterceptor preAuthenticateInterceptor;

    @Autowired
    public WebMVCInterceptorConfig(PreAuthenticateInterceptor preAuthenticateInterceptor) {
        this.preAuthenticateInterceptor = preAuthenticateInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.preAuthenticateInterceptor)
                .addPathPatterns("/articles/**");
    }
}
