package com.kasumov.SpringBootApp.service.impl;

import com.kasumov.SpringBootApp.model.Status;
import com.kasumov.SpringBootApp.model.User;
import com.kasumov.SpringBootApp.repository.UserRepository;
import com.kasumov.SpringBootApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            log.warn("No user found with id: {}", id);
            return null;
        }
        User user = optionalUser.get();
        log.info("Found user by id: {}", id);
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        log.info("Found {} users", users.size());
        return users;
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            log.warn("No user found with username: {}", username);
        } else {
            log.info("Found user by username: {}", username);
        }
        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            log.warn("No user found with email: {}", email);
        } else {
            log.info("Found user by email: {}", email);
        }
        return optionalUser;
    }

    @Override
    public User create(User user) {
        User newUser = new User();
        newUser.setUserName(user.getUserName());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(user.getRole());
        newUser.setStatus(Status.ACTIVE);
        User registeredUser = userRepository.save(newUser);
        log.info("User successfully registered: {}", registeredUser);
        return registeredUser;
    }

    @Override
    public User update(User user) {
        log.info("Updating user: {}", user);
        user.setStatus(Status.ACTIVE);
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            log.warn("No user found with id: {}", id);
        } else {
            User user = optionalUser.get();
            log.info("Deleting user by id: {}", id);
            user.setStatus(Status.DELETED);
            userRepository.save(user);
        }
    }
}