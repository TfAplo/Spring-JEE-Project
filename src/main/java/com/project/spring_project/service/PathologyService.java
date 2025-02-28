package com.project.spring_project.service;

import com.project.spring_project.entity.Pathology;
import com.project.spring_project.entity.User;
import com.project.spring_project.entity.User_Pathology;
import com.project.spring_project.repository.PathologyRepository;
import com.project.spring_project.repository.UserPathologyRepository;
import com.project.spring_project.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PathologyService {
    private final PathologyRepository pathologyRepository;



    public PathologyService( PathologyRepository pathologyRepository) {
        this.pathologyRepository = pathologyRepository;

    }

    public List<Pathology> getAllPathologies() {
        return pathologyRepository.findAll();
    }


}

