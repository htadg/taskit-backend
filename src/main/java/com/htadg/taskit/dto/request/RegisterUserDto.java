package com.htadg.taskit.dto.request;

import lombok.Data;

@Data
public class RegisterUserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private boolean active = true;
}