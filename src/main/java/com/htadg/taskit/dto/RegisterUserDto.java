package com.htadg.taskit.dto;

import java.util.Collection;

import lombok.Data;

@Data
public class RegisterUserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    private Collection<String> roles;
}