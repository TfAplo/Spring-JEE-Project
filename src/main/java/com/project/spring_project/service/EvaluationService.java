package com.project.spring_project.service;

import com.project.spring_project.entity.Evaluation;
import com.project.spring_project.entity.User;
import com.project.spring_project.repository.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    public Evaluation saveEvaluation(Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }
}
