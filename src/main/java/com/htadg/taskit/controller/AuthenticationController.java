package com.htadg.taskit.controller;

import com.htadg.taskit.dto.request.LoginUserDto;
import com.htadg.taskit.dto.request.RegisterUserDto;
import com.htadg.taskit.dto.response.LoginResponseDto;
import com.htadg.taskit.entity.User;
import com.htadg.taskit.exception.TaskItServiceException;
import com.htadg.taskit.service.AuthenticationService;
import com.htadg.taskit.service.JwtService;
import com.htadg.taskit.service.TaskitUserDetailsService;
import com.htadg.taskit.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
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
    public ResponseEntity<String> register(@RequestBody RegisterUserDto user) {
        ResponseEntity<String> response;
        try {
            User registeredUser = authenticationService.signup(user);
            response = ResponseEntity.ok(JsonUtil.toJson(registeredUser));
        } catch (TaskItServiceException e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody LoginUserDto loginUserDto) {
        ResponseEntity<String> response;
        try {
            User authenticatedUser = authenticationService.authenticate(loginUserDto);
            String jwtToken = jwtService.generateToken(authenticatedUser);

            LoginResponseDto loginResponse = new LoginResponseDto();
            loginResponse.setToken(jwtToken);
            loginResponse.setExpiresIn(jwtService.getExpirationTime());

            response = ResponseEntity.ok(JsonUtil.toJson(loginResponse));
        } catch (TaskItServiceException e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return response;
    }
}
