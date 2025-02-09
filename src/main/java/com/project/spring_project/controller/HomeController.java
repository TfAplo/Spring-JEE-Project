package com.project.spring_project.controller;

import com.project.spring_project.entity.Activity;
import com.project.spring_project.entity.User;
import com.project.spring_project.entity.User_Pathology;
import com.project.spring_project.repository.UserPathologyRepository;
import com.project.spring_project.service.ActivityService;
import com.project.spring_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.List;


@Controller
public class HomeController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserPathologyRepository userPathologyRepository;

    @GetMapping("/home")
    public String home(Model model) {
        System.out.println("dans /homecontroller");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User loggedInUser = (User) authentication.getPrincipal();

        if (loggedInUser == null) {
            model.addAttribute("recommendedActivities", Collections.emptyList());
            model.addAttribute("userPathologies", Collections.emptyList());
        }
        else {
            model.addAttribute("username", loggedInUser.getUsername());
            model.addAttribute("age", loggedInUser.getAge());
            List<User_Pathology> userPathologies = userPathologyRepository.findByUserId(loggedInUser.getId());
            model.addAttribute("userPathologies", userPathologies);
            List<Activity> recommendedActivities = activityService.getRecommendedActivities(loggedInUser.getId());
            model.addAttribute("recommendedActivities", recommendedActivities);

        }

        return "home";
    }
}
