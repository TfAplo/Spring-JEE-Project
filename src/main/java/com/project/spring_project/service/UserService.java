package com.project.spring_project.service;

import com.project.spring_project.entity.Pathology;
import com.project.spring_project.entity.User_Pathology;
import com.project.spring_project.entity.User_PathologyId;
import com.project.spring_project.repository.PathologyRepository;
import com.project.spring_project.repository.UserPathologyRepository;
import com.project.spring_project.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.project.spring_project.entity.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

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
            throw new IllegalArgumentException("Nom d'utilisateur déjà pris");
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
                    throw new IllegalArgumentException("Pathologie non trouvée");
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
}
