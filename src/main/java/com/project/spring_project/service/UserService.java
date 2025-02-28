package com.project.spring_project.service;

import com.project.spring_project.entity.Pathology;
import com.project.spring_project.entity.User_Pathology;
import com.project.spring_project.entity.User_PathologyId;
import com.project.spring_project.repository.PathologyRepository;
import com.project.spring_project.repository.UserPathologyRepository;
import com.project.spring_project.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.project.spring_project.entity.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PathologyRepository pathologyRepository;
    private final UserPathologyRepository userPathologyRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PathologyRepository pathologyRepository, UserPathologyRepository userPathologyRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.pathologyRepository = pathologyRepository;
        this.userPathologyRepository = userPathologyRepository;
    }

    public void registerUser(User user, List<String> pathologies) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        if (pathologies != null && !pathologies.isEmpty()) {
            for (String pathologyName : pathologies) {
                Optional<Pathology> pathology = pathologyRepository.findByDescription(pathologyName);
                if (pathology.isPresent()) {
                    User_PathologyId userPathologyId = new User_PathologyId();
                    userPathologyId.setUser_id(savedUser.getId_user());
                    userPathologyId.setPathology_id(pathology.get().getId_pathology());

                    User_Pathology userPathology = new User_Pathology();
                    userPathology.setId(userPathologyId);
                    userPathology.setUser(savedUser);
                    userPathology.setPathology(pathology.get());

                    userPathologyRepository.save(userPathology);
                }else{
                    throw new IllegalArgumentException("Pathology not found");
                }
            }
        }

    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    public User updateUser(int id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setPassword(updatedUser.getPassword());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public void updateUserName(int userId, String newName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setUsername(newName);
        userRepository.save(user);
    }

    @Transactional
    public void addPathologyToUser(int userId, int pathologyId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Pathology pathology = pathologyRepository.findById(pathologyId)
                .orElseThrow(() -> new EntityNotFoundException("Pathology not found"));

        if(userPathologyRepository.existsByUserAndPathology(userId, pathologyId)) {
            throw new IllegalStateException("Association already exists");
        }

        User_Pathology up = new User_Pathology();
        up.setUser(user);
        up.setPathology(pathology);
        userPathologyRepository.save(up);
    }

    @Transactional
    public void removePathologyFromUser(int userId, int pathologyId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Pathology pathology = pathologyRepository.findById(pathologyId)
                .orElseThrow(() -> new EntityNotFoundException("Pathology not found"));

        User_Pathology userPathology = userPathologyRepository.findByUserAndPathology(user.getId_user(), pathology.getId_pathology())
                .orElseThrow(() -> new EntityNotFoundException("Association not found"));

        userPathologyRepository.delete(userPathology);
    }
}
