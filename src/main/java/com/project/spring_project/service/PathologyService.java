package com.project.spring_project.service;

import com.project.spring_project.entity.Pathology;
import com.project.spring_project.repository.PathologyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PathologyService {
    private final PathologyRepository pathologyRepository;

    public PathologyService(PathologyRepository pathologyRepository) {
        this.pathologyRepository = pathologyRepository;
    }

    public List<Pathology> getAllPathologies() {
        return pathologyRepository.findAll();
    }
}

