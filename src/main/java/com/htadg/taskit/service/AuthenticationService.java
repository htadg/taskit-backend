package com.htadg.taskit.service;

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
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public User signup(RegisterUserDto input) {
        User user = new User();
        user.setFirstName(input.getFirstName());
        user.setLastName(input.getLastName());
        user.setEmail(input.getEmail());
        user.setUserName(input.getUserName());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setActive(true);
        user.setRoles(roleRepository.findByIdIn(input.getRoles()));
        userRepository.save(user);

        return user;
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(input.getUserName(), input.getPassword())
        );

        return userRepository.findByUserName(input.getUserName());
    }
}
