package com.project.spring_project.service;

import com.project.spring_project.entity.Activity;
import com.project.spring_project.entity.Evaluation;
import com.project.spring_project.entity.User;
import com.project.spring_project.repository.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    public Evaluation saveEvaluation(Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }

    public List<Evaluation> findByActivityAndUser(Activity activity, User user) {
        return evaluationRepository.findByActivityAndUser(activity, user);
    }


}
