package com.project.spring_project.service;

import com.project.spring_project.entity.Program;
import com.project.spring_project.entity.User;
import com.project.spring_project.repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProgramService {

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private UserService UserService;

    public List<Program> getAllPrograms() {
        return programRepository.findAll();
    }

    public Program getProgram(Long id) {
        return programRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Programme non trouvé"));
    }

    @Transactional
    public Program createProgram(String name, int userId) {
        Program program = new Program();
        program.setName(name);

        User user = UserService.getUserById(userId).orElse(null);
        program.setUser(user);

        return programRepository.save(program);
    }

    @Transactional
    public Program updateProgram(Long id, String name) {
        Program program = programRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Programme non trouvé"));
        program.setName(name);
        return programRepository.save(program);
    }

    @Transactional
    public void deleteProgram(Long id) {
        programRepository.deleteById(id);
    }
}

