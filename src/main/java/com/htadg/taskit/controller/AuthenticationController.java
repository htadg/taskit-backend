package com.htadg.taskit.controller;

import com.htadg.taskit.dto.TaskItResponseBuilder;
import com.htadg.taskit.dto.request.LoginUserDto;
import com.htadg.taskit.dto.request.RegisterUserDto;
import com.htadg.taskit.dto.response.LoginResponseDto;
import com.htadg.taskit.entity.User;
import com.htadg.taskit.exception.TaskItServiceException;
import com.htadg.taskit.service.AuthenticationService;
import com.htadg.taskit.service.JwtService;
import com.htadg.taskit.service.TaskitUserDetailsService;
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
    public ResponseEntity<Object> register(@RequestBody RegisterUserDto user) {
        TaskItResponseBuilder response;
        try {
            User registeredUser = authenticationService.signup(user);
            response = TaskItResponseBuilder.status(HttpStatus.OK).message("User successfully created.").data(registeredUser);
        } catch (TaskItServiceException e) {
            response = TaskItResponseBuilder.status(HttpStatus.BAD_REQUEST).message(e.getMessage());
        } catch (Exception e) {
            response = TaskItResponseBuilder.status(HttpStatus.INTERNAL_SERVER_ERROR).message(e.getMessage());
        }

        return response.build();
    }

    @PostMapping("/getAuthToken")
    public ResponseEntity<Object> authenticate(@RequestBody LoginUserDto loginUserDto) {
        TaskItResponseBuilder response;
        try {
            User authenticatedUser = authenticationService.authenticate(loginUserDto);
            String jwtToken = jwtService.generateToken(authenticatedUser);

            LoginResponseDto loginResponse = new LoginResponseDto();
            loginResponse.setToken(jwtToken);
            loginResponse.setExpiresIn(jwtService.getExpirationTime());

            response = TaskItResponseBuilder.status(HttpStatus.OK).data(loginResponse);
        } catch (TaskItServiceException e) {
            response = TaskItResponseBuilder.status(HttpStatus.BAD_REQUEST).data(e.getMessage());
        } catch (Exception e) {
            response = TaskItResponseBuilder.status(HttpStatus.INTERNAL_SERVER_ERROR).data(e.getMessage());
        }

        return response.build();
    }
}
