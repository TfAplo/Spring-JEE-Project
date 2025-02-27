package com.project.spring_project.service;

import com.project.spring_project.entity.Activity;
import com.project.spring_project.entity.User;
import com.project.spring_project.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;


    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    public List<Activity> getRecommendedActivities(int userId) {
        return activityRepository.findActivitiesByUserId(userId);
    }

    public List<Activity> searchActivities(String query) {
        return activityRepository.findByNameContaining(query);
    }

    public Activity getActivityById(Long id) {
        return activityRepository.findById(id).orElse(null);
    }

    public Activity saveActivity(Activity activity) {
        return activityRepository.save(activity);
    }
}
