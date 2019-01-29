package com.example.myProject.config;

import com.example.myProject.custom.PreAuthenticate;
import com.example.myProject.entities.User;
import com.example.myProject.enums.Role;
import com.example.myProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class PreAuthenticateInterceptor extends HandlerInterceptorAdapter {
    UserService userService;

    @Autowired
    public PreAuthenticateInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod method = (HandlerMethod) handler;

        if (method.hasMethodAnnotation(PreAuthenticate.class)) {
            boolean loggedin = method.getMethodAnnotation(PreAuthenticate.class).loggedIn();
            String inRole = method.getMethodAnnotation(PreAuthenticate.class).inRole();

            if (loggedin && request.getSession().getAttribute("userId") == null) {
                response.sendRedirect("/users/login");
                return false;
            }

            User user = userService.findByUsername((String) request.getSession().getAttribute("username"));
            if (user == null) {
                response.sendRedirect("/users/login");
                return false;
            }

            if (user.getRole().ordinal() < Role.valueOf(inRole).ordinal()) {
                response.sendRedirect("/users/login");
                return false;
            }
        }

        return super.preHandle(request, response, handler);
    }
    }

