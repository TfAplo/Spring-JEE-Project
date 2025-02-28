package com.project.spring_project.controller;

import com.project.spring_project.entity.*;
import com.project.spring_project.service.EvaluationService;
import com.project.spring_project.service.ProgramService;
import com.project.spring_project.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/program")
public class ProgramController {

    @Autowired
    private ProgramService programService;

    @Autowired
    private EvaluationService evaluationService;

    @GetMapping
    public String showUserPrograms(Model model, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();

        model.addAttribute("currentUser", currentUser);

        List<Program> programs = programService.getProgramsByUser(currentUser);

        Map<Program, Double> programAverages = new HashMap<>();

        for (Program program : programs) {
            List<Evaluation> userEvaluations = new ArrayList<>();

            for (Activity_Program activityProgram : program.getActivities()) {
                List<Evaluation> evaluations = evaluationService.findByActivityAndUser(activityProgram.getActivity(), currentUser);
                userEvaluations.addAll(evaluations);
            }

            double moyenne = calculerMoyenne(userEvaluations);
            programAverages.put(program, moyenne);
        }

        List<Program> userPrograms = programService.getProgramsByUser(currentUser);
        model.addAttribute("programs", userPrograms);
        model.addAttribute("programAverages", programAverages);

        return "program";
    }

    private double calculerMoyenne(List<Evaluation> evaluations) {
        if (evaluations == null || evaluations.isEmpty()) {
            return 0.0;
        }
        return evaluations.stream()
                .mapToDouble(Evaluation::getRate)
                .average()
                .orElse(0.0);
    }


    @PostMapping
    public String createProgram(@RequestParam String name, HttpSession session, RedirectAttributes redirectAttributes) {
        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User loggedInUser = (User) authentication.getPrincipal();
            if (loggedInUser == null) {
                redirectAttributes.addFlashAttribute("error", "User not logged in");
                return "redirect:/login";
            }

            int userId = loggedInUser.getId();
            Program program = programService.createProgram(name, userId);
            redirectAttributes.addFlashAttribute("successMessage", "Program created successfully");
            return "redirect:/program";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error creating program");
            return "redirect:/program";
        }
    }

    @PostMapping("/{id}/update")
    public String updateProgram(@PathVariable Long id, @RequestParam String name) {
        programService.updateProgram(id, name);
        return "redirect:/program";
    }

    @PostMapping("/{id}/delete")
    public String deleteProgram(@PathVariable Long id) {
        programService.deleteProgram(id);
        return "redirect:/program";
    }

    @PostMapping("/{id}/select")
    public String selectProgram(@PathVariable Long id, HttpSession session) {
        session.setAttribute("selectedProgramId", id);
        return "redirect:/activities";
    }
}