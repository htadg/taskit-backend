package com.htadg.taskit.controller;

import com.htadg.taskit.dto.LoginResponseDto;
import com.htadg.taskit.dto.LoginUserDto;
import com.htadg.taskit.dto.RegisterUserDto;
import com.htadg.taskit.entity.User;
import com.htadg.taskit.service.AuthenticationService;
import com.htadg.taskit.service.JwtService;
import com.htadg.taskit.service.TaskitUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, TaskitUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto user) {
        User registeredUser = authenticationService.signup(user);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        if (authenticatedUser == null) {
            ResponseEntity.badRequest().body("{\"error\": \"Invalid Credentials\"}");
        }
        String jwtToken = jwtService.generateToken(authenticatedUser);
        
        LoginResponseDto loginResponse = new LoginResponseDto();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}
