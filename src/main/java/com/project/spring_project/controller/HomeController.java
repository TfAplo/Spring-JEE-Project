package com.project.spring_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

//    @Autowired
//    public HomeController(){};

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
