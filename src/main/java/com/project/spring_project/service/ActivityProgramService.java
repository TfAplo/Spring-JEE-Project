package com.project.spring_project.service;

import com.project.spring_project.entity.Activity_Program;
import com.project.spring_project.entity.Activity_ProgramId;
import com.project.spring_project.repository.ActivityProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityProgramService{
    @Autowired
    private ActivityProgramRepository activityProgramRepository;

    public void save(Activity_Program activityProgram) {
        activityProgramRepository.save(activityProgram);
    }

    public boolean existsById(Activity_ProgramId id) {
        return activityProgramRepository.existsById(id);
    }
}
