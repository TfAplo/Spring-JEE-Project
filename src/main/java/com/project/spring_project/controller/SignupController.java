package com.project.spring_project.controller;

import com.project.spring_project.entity.Pathology;
import com.project.spring_project.entity.User;
import com.project.spring_project.service.PathologyService;
import com.project.spring_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SignupController {

    private final UserService userService;
    private final PathologyService pathologyService;

    @Autowired
    public SignupController(UserService userService, PathologyService pathologyService) {
        this.userService = userService;
        this.pathologyService = pathologyService;
    }

    @GetMapping("/signup")
    public String showSignupPage(Model model) {
        List<Pathology> pathologies = pathologyService.getAllPathologies();
        model.addAttribute("pathologies", pathologies);
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignup(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("age") int age,
            @RequestParam(value = "pathologies", required = false) List<String> pathologies,
            Model model) {

        try {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setAge(age);

            userService.registerUser(user,pathologies);
            return "redirect:/login?signupSuccess"; // Redirection après réussite
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de l'inscription : " + e.getMessage());
            List<Pathology> paths = pathologyService.getAllPathologies();
            model.addAttribute("pathologies", paths);
            return "signup";
        }
    }
}
