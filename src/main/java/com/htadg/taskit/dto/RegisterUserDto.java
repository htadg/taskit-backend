package com.htadg.taskit.dto;

import java.util.Collection;

import lombok.Data;

@Data
public class RegisterUserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private boolean superAdmin = false;
    private boolean active = true;
}