package com.project.spring_project.controller;

import com.project.spring_project.entity.User;
import com.project.spring_project.entity.User_Pathology;
import com.project.spring_project.repository.UserPathologyRepository;
import com.project.spring_project.service.PathologyService;
import com.project.spring_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserPathologyRepository userPathologyRepository;

    @Autowired
    private PathologyService pathologyService;

    @ExceptionHandler({Exception.class})
    public String handleException(Exception ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error";
    }

    public ProfileController(UserService userService, PathologyService pathologyService) {
        this.userService = userService;
        this.pathologyService = pathologyService;
    }

    @GetMapping
    public String showProfile(Model model, Authentication authentication) {
        try {
            if (authentication == null || !authentication.isAuthenticated()) {
                return "redirect:/login";
            }

            Object principal = authentication.getPrincipal();
            if (!(principal instanceof User)) {
                throw new ClassCastException("Invalid user type");
            }

            User user = (User) authentication.getPrincipal();

            model.addAttribute("user", user);



            List<User_Pathology> userPathologies = userPathologyRepository.findByUserId(user.getId());
            model.addAttribute("userPathologies", userPathologies);
            model.addAttribute("allPathologies", pathologyService.getAllPathologies());

            return "profile";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error loading profile : " + e.getMessage());
            return "error";
        }
    }


    @PostMapping("/add-pathology")
    public String addPathology(@RequestParam("pathologyId") int pathologyId,
                               Authentication authentication,
                               RedirectAttributes redirectAttributes) {
        try {
            User user = (User) authentication.getPrincipal();
            userService.addPathologyToUser(user.getId_user(), pathologyId);
            redirectAttributes.addFlashAttribute("successMessage", "Pathology added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error : " + e.getMessage());
        }

        return "redirect:/profile";
    }

    @PostMapping("/remove-pathology/{pathologyId}")
    public String removePathology(@PathVariable("pathologyId") int pathologyId,
                                  Authentication authentication,
                                  RedirectAttributes redirectAttributes) {
        try {
            User user = (User) authentication.getPrincipal();
            userService.removePathologyFromUser(user.getId_user(), pathologyId);
            redirectAttributes.addFlashAttribute("successMessage", "Pathology deleted !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error : " + e.getMessage());
        }
        return "redirect:/profile";
    }
}