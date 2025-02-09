package com.project.spring_project.controller;

import com.project.spring_project.entity.Program;
import com.project.spring_project.entity.User;
import com.project.spring_project.service.ProgramService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/program")
public class ProgramController {

    @Autowired
    private ProgramService programService;

    @GetMapping
    public String showPrograms(Model model, HttpSession session) {
        model.addAttribute("program", programService.getAllPrograms());
        Long selectedProgramId = (Long) session.getAttribute("selectedProgramId");
        model.addAttribute("selectedProgramId", selectedProgramId);
        return "program";
    }

    @PostMapping
    public String createProgram(@RequestParam String name, HttpSession session, RedirectAttributes redirectAttributes) {
        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User loggedInUser = (User) authentication.getPrincipal();
            if (loggedInUser == null) {
                redirectAttributes.addFlashAttribute("error", "Utilisateur non connecté");
                return "redirect:/login";
            }

            int userId = loggedInUser.getId();
            Program program = programService.createProgram(name, userId);
            redirectAttributes.addFlashAttribute("message", "Programme créé avec succès");
            return "redirect:/program";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la création du programme");
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