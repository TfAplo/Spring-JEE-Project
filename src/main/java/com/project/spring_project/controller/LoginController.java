package com.project.spring_project.controller;

import com.project.spring_project.entity.User;
import com.project.spring_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home() {
        return userService.getUserById(1).map(User::getUsername).orElse("User not found");
    }
}
