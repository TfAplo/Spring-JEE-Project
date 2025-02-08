package com.project.spring_project.controller;
import com.project.spring_project.entity.Activity;
import com.project.spring_project.entity.Evaluation;
import com.project.spring_project.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private ActivityService activityService;

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