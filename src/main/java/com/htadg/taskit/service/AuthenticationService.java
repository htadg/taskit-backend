package com.htadg.taskit.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.htadg.taskit.dto.LoginUserDto;
import com.htadg.taskit.dto.RegisterUserDto;
import com.htadg.taskit.entity.User;
import com.htadg.taskit.repository.RoleRepository;
import com.htadg.taskit.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@Setter
@Getter
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public User signup(RegisterUserDto input) {
        User user = new User();
        user.setUsername(input.getUsername());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setFirstName(input.getFirstName());
        user.setLastName(input.getLastName());
        user.setEmail(input.getEmail());
        user.setSuperAdmin(input.isSuperAdmin());
        user.setActive(input.isActive());
        userRepository.save(user);

        return user;
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword())
        );

        return userRepository.findByUsername(input.getUsername());
    }
}
