package com.example.myProject.controllers;

import com.example.myProject.bindingModel.UserBindingModel;
import com.example.myProject.bindingModel.UserLoginBindingModel;
import com.example.myProject.entities.User;
import com.example.myProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ModelAndView login(ModelAndView modelAndView){
        modelAndView.setViewName("user/login");
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView loginConfirmation(ModelAndView modelAndView, HttpSession httpSession,
                                          @ModelAttribute("user")UserLoginBindingModel userLoginBindingModel){
        boolean checkIfUserExists = userService.checkIfUserExists(userLoginBindingModel);
        if(!checkIfUserExists){
            modelAndView.setViewName("user/login");
            modelAndView.addObject("userModel", new UserLoginBindingModel());
            return modelAndView;
        }

        boolean checkPasswordMatch = userService.checkIfPasswordMatches(userLoginBindingModel);

        if(!checkPasswordMatch){
            modelAndView.setViewName("user/login");
            modelAndView.addObject("userModel", new UserLoginBindingModel());
            return modelAndView;
        }

        User userDataBase = userService.findByUsername(userLoginBindingModel.getUsername());

        httpSession.setAttribute("userId", userDataBase.getId());
        httpSession.setAttribute("username", userDataBase.getUsername());
        httpSession.setAttribute("role", userDataBase.getRole().name());
        httpSession.setAttribute("email",userDataBase.getEmail());
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView register(ModelAndView modelAndView, BindingResult bindingResult){
        modelAndView.setViewName("user/register");
        modelAndView.addObject("errors",bindingResult.getAllErrors());
        modelAndView.addObject("userModel", new UserBindingModel());

        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerProcess(@Valid UserBindingModel userBindingModel, ModelAndView modelAndView, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            modelAndView.addObject("errors", bindingResult.hasErrors());
            modelAndView.addObject("userModel",userBindingModel);
            modelAndView.setViewName("user/register");

            return modelAndView;
        }

        try {
            this.userService.registerUser(userBindingModel);
        } catch (IllegalStateException e) {
            modelAndView.setViewName("user/register");
            return modelAndView;
        }

        modelAndView.addObject("userModel", new UserBindingModel());
        modelAndView.setViewName("user/login");
        return modelAndView;


    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession){
        httpSession.invalidate();
        return "redirect:/";
    }

    @GetMapping("/profile")
    public ModelAndView profile(ModelAndView modelAndView,HttpServletRequest request){
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String email = (String) session.getAttribute("email");

        modelAndView.addObject("username",username);
        modelAndView.addObject("email",email);


        modelAndView.setViewName("user/profile");

        return modelAndView;
    }
}
