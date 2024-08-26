package com.htadg.taskit.service;

import com.htadg.taskit.entity.User;
import com.htadg.taskit.exception.TaskItServiceException;
import com.htadg.taskit.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Setter
@Getter
@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByUsername(String username) {
        try {
            log.debug("Finding user by username : {}", username);
            return userRepository.findByUsername(username);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    public void save(User user) throws TaskItServiceException {
        try {
            log.info("Encoding password for user {}", user.getUsername());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            log.info("New user created : {}", user);
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage(), e);
            throw new TaskItServiceException(e);
        }
    }

}
