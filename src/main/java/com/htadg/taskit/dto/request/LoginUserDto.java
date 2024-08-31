package com.htadg.taskit.dto.request;

import lombok.Data;

@Data
public class LoginUserDto {
    private String username;
    private String password;
}