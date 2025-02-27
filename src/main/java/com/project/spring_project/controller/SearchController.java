package com.project.spring_project.controller;
import com.project.spring_project.entity.Activity;
import com.project.spring_project.entity.Evaluation;
import com.project.spring_project.entity.EvaluationId;
import com.project.spring_project.entity.User;
import com.project.spring_project.repository.EvaluationRepository;
import com.project.spring_project.service.ActivityService;
import com.project.spring_project.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.List;
import java.util.Optional;

@Controller
public class SearchController {

    @Autowired
    private ActivityService activityService;
    @Autowired
    private EvaluationService evaluationService;

    @GetMapping("/activities")
    public String activities(Model model) {
        List<Activity> activities = activityService.getAllActivities();
        activities.forEach(activity -> {
            double moyenne = calculerMoyenne(activity.getEvaluations());
            activity.setAverageRate(moyenne);
        });
        model.addAttribute("activities", activities);
        return "activities";
    }

    @GetMapping("/search")
    public String searchActivities(@RequestParam String query, Model model) {
        System.out.println(query);
        List<Activity> activities = activityService.searchActivities(query);
        activities.forEach(activity -> {
            double moyenne = calculerMoyenne(activity.getEvaluations());
            activity.setAverageRate(moyenne);
        });
        model.addAttribute("activities", activities);
        return "Activity-list :: ActivityList";
    }

    @GetMapping("/activity")
    public String getActivityDetails(@RequestParam Long id, Model model, @AuthenticationPrincipal User currentUser) {
        Activity activity = activityService.getActivityById(id);

        double userRating = activity.getUserRating(currentUser.getId());

        double moyenne = calculerMoyenne(activity.getEvaluations());
        activity.setAverageRate(moyenne);

        model.addAttribute("activity", activity);
        model.addAttribute("userRating", userRating);
        return "Pop-upRating :: Popup";
    }

    @PostMapping("/activity/rate")
    public String rateActivity(@RequestParam Long activityId,
                               @RequestParam double rating,
                               @AuthenticationPrincipal User currentUser,
                               RedirectAttributes redirectAttributes) {

        Activity activity = activityService.getActivityById(activityId);
        if (activity == null) {
            return "redirect:/activities";
        }

        Optional<Evaluation> existingEvaluation = activity.getEvaluations().stream()
                .filter(e -> e.getUser().getId() == currentUser.getId())
                .findFirst();

        if (existingEvaluation.isPresent()) {
            existingEvaluation.get().setRate(rating);
        } else {
            Evaluation newEvaluation = new Evaluation();
            EvaluationId evaluationId = new EvaluationId();
            evaluationId.setIdActivity(activity.getId_activity());
            evaluationId.setIdUser(currentUser.getId());

            newEvaluation.setId(evaluationId);
            newEvaluation.setActivity(activity);
            newEvaluation.setUser(currentUser);
            newEvaluation.setRate(rating);

            activity.getEvaluations().add(newEvaluation);
            evaluationService.saveEvaluation(newEvaluation);
        }

        double moyenne = calculerMoyenne(activity.getEvaluations());
        activity.setAverageRate(moyenne);
        activityService.saveActivity(activity);

        redirectAttributes.addFlashAttribute("successMessage", "Rating updated successfully!");

        return "redirect:/activities";
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
}