package com.project.spring_project.controller;
import com.project.spring_project.entity.*;
import com.project.spring_project.service.ActivityProgramService;
import com.project.spring_project.service.ActivityService;
import com.project.spring_project.service.EvaluationService;
import com.project.spring_project.service.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class SearchController {

    @Autowired
    private ActivityService activityService;
    @Autowired
    private EvaluationService evaluationService;
    @Autowired
    private ProgramService programService;
    @Autowired
    private ActivityProgramService activityProgramService;

    @GetMapping("/activities")
    public String activities(Model model) {
        model.addAttribute("userActivities", getUserActivities());

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

        model.addAttribute("userActivities", getUserActivities());
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

        model.addAttribute("act", true);
        model.addAttribute("activity", activity);
        model.addAttribute("userRating", userRating);
        return "Pop-up :: Popup";
    }

    @GetMapping("/progDetails")
    public String getProgramDetails(@RequestParam Long id, Model model, @AuthenticationPrincipal User currentUser) {
        Activity activity = activityService.getActivityById(id);
        List<Program> programs = programService.getAllPrograms();

        model.addAttribute("activity", activity);
        model.addAttribute("prog", true);
        model.addAttribute("programs", programs);
        return "Pop-up :: Popup";
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

    @PostMapping("/activity/addToProg")
    public String addActivityToProg(@RequestParam Long activityId,
                                    @RequestParam(required = false) List<Long> selectedProgramIds,
                               @AuthenticationPrincipal User currentUser,
                               RedirectAttributes redirectAttributes){

        Activity activity = activityService.getActivityById(activityId);
        if (activity == null || selectedProgramIds == null || selectedProgramIds.isEmpty()) {
            return "redirect:/activities";
        }
        for (Long programId : selectedProgramIds) {
            Program program = programService.getProgram(programId);
            if (program != null) {
                //on verifie si l'association existe deja
                Activity_ProgramId activityProgramId = new Activity_ProgramId(program.getId_program(), activity.getId_activity());
                if (!activityProgramService.existsById(activityProgramId)) {
                    // Création d'un nouvel Activity_Program
                    Activity_Program activityProgram = new Activity_Program();
                    activityProgram.setId(activityProgramId);
                    activityProgram.setActivity(activity);
                    activityProgram.setProgram(program);

                    activityProgramService.save(activityProgram);
                }
            }
        }

        redirectAttributes.addFlashAttribute("successMessage", "Activity added successfully!");

        return "redirect:/activities";
    }

    private List<Activity> getUserActivities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedInUser = (User) authentication.getPrincipal();
        //on recupere toutes les activitées que l'user à ajouter dans son programme
        List<Activity> userActivities = new ArrayList<Activity>();
        for (Program p:programService.getAllPrograms()){
            if (p.getUser().getId_user() == loggedInUser.getId_user()){
                for (Activity_Program act: p.getActivities()){
                    userActivities.add(act.getActivity());
                }
            }
        }
        return userActivities;
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